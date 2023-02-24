package com.example.configcenter.vo.test;

import jakarta.validation.constraints.NotNull;

public class TestVO {
    @NotNull
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "TestVO{" +
                "user=" + user +
                '}';
    }
}
