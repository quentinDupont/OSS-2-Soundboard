package fr.legrand.oss117soundboard.data.manager.file;

import java.util.List;

import fr.legrand.oss117soundboard.data.entity.Reply;

/**
 * Created by Benjamin on 30/09/2017.
 */

public interface FileManager {
    List<Reply> buildReplyListFromResource();
}
