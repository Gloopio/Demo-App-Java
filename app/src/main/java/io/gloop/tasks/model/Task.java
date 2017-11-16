package io.gloop.tasks.model;

import io.gloop.GloopObject;

/**
 * Created by Alex Untertrifaller on 21.10.16.
 */
public class Task extends GloopObject {

    private String description;
    private boolean done;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
