package com.ecommerce.Ally.AllyMongo;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "orders")
public class Order {
    @Id
    public ObjectId _id;

    public double TotalDue;
    public Status Status;
    public List<LineItem> items;
    public List<Payment> payments = new ArrayList<>();

    // ObjectId needs to be converted to string
    public String get_id() { return _id.toHexString(); }
    public void set_id(ObjectId _id) { this._id = _id; }

    public double getTotalDue() {
        return TotalDue;
    }

    public void setTotalDue(double totalDue) {
        TotalDue = totalDue;
    }

    public com.ecommerce.Ally.AllyMongo.Status getStatus() {
        return Status;
    }
    public void setStatus(com.ecommerce.Ally.AllyMongo.Status status) {
        Status = status;
    }

    public List<LineItem> getItems() {
        return items;
    }

    public void setItems(List<LineItem> items) {
        this.items = items;
    }

    public List<Payment> getPayments() {
        if(payments.equals(null))
            payments = new ArrayList<Payment>();
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }
}
