package com.ecommerce.Ally.AllyMongo;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "orders")
public class Order {
    @Id
    public ObjectId _id;

    public double TotalDue;
    public Status Status;
    public List<LineItem> items;

    // ObjectId needs to be converted to string
    public String get_id() { return _id.toHexString(); }
    public void set_id(ObjectId _id) { this._id = _id; }

    public double getTotalDue() {
        return TotalDue;
    }

    public com.ecommerce.Ally.AllyMongo.Status getStatus() {
        return Status;
    }

    public List<LineItem> getItems() {
        return items;
    }

    public void setTotalDue(double totalDue) {
        TotalDue = totalDue;
    }

    public void setStatus(com.ecommerce.Ally.AllyMongo.Status status) {
        Status = status;
    }

    public void setItems(List<LineItem> items) {
        this.items = items;
    }
}
