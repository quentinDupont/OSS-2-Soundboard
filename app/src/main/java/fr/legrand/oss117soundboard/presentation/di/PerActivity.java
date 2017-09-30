package fr.legrand.oss117soundboard.presentation.di;

/**
 * Created by Benjamin on 12/09/2017.
 */

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Scope
@Retention(RUNTIME)
public @interface PerActivity {}
