package controllers;


import viewmodels.BaseViewModel;

public abstract class BaseController<T extends BaseViewModel> {
    public T model;
}
