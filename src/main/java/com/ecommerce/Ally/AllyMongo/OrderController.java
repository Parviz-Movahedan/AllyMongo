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
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderRepository repository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Order> getAllOrder() {
        return repository.findAll();
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Order getOrderById(@PathVariable("id") ObjectId id) {
        return repository.findBy_id(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
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
