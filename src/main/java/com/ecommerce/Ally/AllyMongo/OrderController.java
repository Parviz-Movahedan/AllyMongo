package com.ecommerce.Ally.AllyMongo;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.List;
@RestController
public class OrderController {
    @Autowired
    private OrderRepository repository;

    @RequestMapping(value = "/order/", method = RequestMethod.GET)
    public List<Order> getAllOrder() {
        return repository.findAll();
    }

    @RequestMapping(value = "/orders/", method = RequestMethod.GET)
    public Document getAllOrdersStatus() {
        List<Order> orders = repository.findAll();
        int NumberWaitingForPayment = 0;
        int NumberShipped = 0;
        for(Order o : orders){
            if(o.getStatus() == Status.WAITING_FOR_PAYMENT)
                NumberWaitingForPayment ++;
            else if (o.getStatus() == Status.SHIPPED)
                NumberShipped++;
        }

        Document result = new Document();
        result.append("numberWaitingForPayment" , NumberWaitingForPayment);
        result.append("numberShipped" , NumberShipped);
        return result;
    }


    @RequestMapping(value = "/order/{id}", method = RequestMethod.GET)
    public Order getOrderById(@PathVariable("id") String id) {
        ObjectId objID = new ObjectId(id);
        Order order = repository.findBy_id(objID);
        return order;
    }

    @RequestMapping(value = "/payment/", method = RequestMethod.POST)
    public Payment createPayment (@Valid @RequestBody Payment payment) throws Exception{
        ObjectId orderid = new ObjectId(payment.orderID);
        Order order = repository.findBy_id(orderid);
        if(order.equals(null))
            throw new Exception("Order " + payment.orderID + " doesn't exist");
        order.payments.add(payment);
        double newTotal = order.getTotalDue()-payment.amount;
        if(newTotal >= 0)
            order.setTotalDue(newTotal);
        else
            throw new Exception("Over Paid");

        if(newTotal == 0)
            order.setStatus(Status.SHIPPED);

        repository.save(order);
        return payment;
    }

    @RequestMapping(value = "/order/", method = RequestMethod.POST)
    public Document createOrder(@Valid @RequestBody Order order) {
        order.set_id(ObjectId.get());


        double total = 0;
        for( LineItem i : order.items){
            total += i.price * i.quantity;
        }

        Document result = new Document();
        result.append("totaldue", total);
        result.append("status", Status.WAITING_FOR_PAYMENT);

        order.setTotalDue(total);
        order.setStatus(Status.WAITING_FOR_PAYMENT);

        repository.save(order);
        return result;
    }
}
