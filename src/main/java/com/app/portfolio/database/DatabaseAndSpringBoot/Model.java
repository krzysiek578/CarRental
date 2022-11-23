package com.app.portfolio.database.DatabaseAndSpringBoot;

public interface Model<ID> {
    void setId(ID id);

    ID getId();
}
