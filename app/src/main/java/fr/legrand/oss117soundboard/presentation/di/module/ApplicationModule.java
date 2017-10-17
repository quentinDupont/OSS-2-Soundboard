package fr.legrand.oss117soundboard.presentation.di.module;

import android.content.Context;

import com.cocosw.favor.FavorAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import fr.legrand.oss117soundboard.data.manager.db.DatabaseManager;
import fr.legrand.oss117soundboard.data.manager.db.DatabaseManagerImpl;
import fr.legrand.oss117soundboard.data.manager.file.FileManager;
import fr.legrand.oss117soundboard.data.manager.file.FileManagerImpl;
import fr.legrand.oss117soundboard.data.manager.sharedpref.SharedPrefManager;
import fr.legrand.oss117soundboard.data.manager.sharedpref.SharedPrefManagerImpl;
import fr.legrand.oss117soundboard.data.manager.sharedpref.SharedPreferences;
import fr.legrand.oss117soundboard.data.repository.ContentRepository;
import fr.legrand.oss117soundboard.data.repository.ContentRepositoryImpl;
import fr.legrand.oss117soundboard.presentation.OSSApplication;

/**
 * Created by Benjamin on 12/09/2017.
 */

@Module
public class ApplicationModule {
    private final OSSApplication application;

    public ApplicationModule(OSSApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context context() {
        return this.application;
    }

    @Provides
    @Singleton
    ContentRepository contentRepository(ContentRepositoryImpl contentRepositoryImpl) {
        return contentRepositoryImpl;
    }

    @Provides
    @Singleton
    FileManager soundFileManager(FileManagerImpl soundFileManagerImpl) {
        return soundFileManagerImpl;
    }

    @Provides
    @Singleton
    SharedPrefManager sharedPrefManager(SharedPrefManagerImpl sharedPrefManagerImpl) {
        return sharedPrefManagerImpl;
    }

    @Provides
    @Singleton
    DatabaseManager databaseManager(DatabaseManagerImpl databaseManager) {
        return databaseManager;
    }

    @Provides
    @Singleton
    SharedPreferences sharedPreferences(Context context) {
        return new FavorAdapter.Builder(context).build().create(SharedPreferences.class);
    }

    @Provides
    @Singleton
    Gson gson() {
        return new GsonBuilder().create();
    }
}
