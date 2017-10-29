package fr.legrand.oss117soundboard.data.entity;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import fr.legrand.oss117soundboard.data.manager.db.DatabaseManager;

/**
 * Created by Benjamin on 30/09/2017.
 */
@Table(database = DatabaseManager.class)
public class Reply extends BaseModel {

    @Column
    @PrimaryKey
    private int id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private boolean favorite;

    @Column
    private int listenCount;

    @Column
    private int timestamp;

    public Reply(int id, String name, String description, int timestamp) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.timestamp = timestamp;
        this.favorite = false;
    }

    public Reply() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Reply && ((Reply) o).getId() == this.id;
    }


    public int getListenCount() {
        return listenCount;
    }

    public void setListenCount(int listenCount) {
        this.listenCount = listenCount;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }
}
