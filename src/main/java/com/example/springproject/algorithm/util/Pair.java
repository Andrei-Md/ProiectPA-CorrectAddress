package com.example.springproject.algorithm.util;

/**
 * generic class that makes a pair
 * @param <Left>
 * @param <Right>
 */
public class Pair<Left,Right> {
    private Left left;
    private Right right;

    /**
     * constructor for pair
     * @param left left part of pair
     * @param right right part of pair
     */
    public Pair(Left left, Right right){
        this.left = left;
        this.right = right;
    }

    /**
     * get the left part of pair
     * @return left part of pair
     */
    public Left getKey() {
        return left;
    }

    /**
     * set the left part of pair
     * @param left left part of pair
     */
    public void setKey(Left left) {
        this.left = left;
    }

    /**
     * get the right part of pair
     * @return right part of pair
     */
    public Right getValue() {
        return right;
    }

    /**
     * set the right part of pair
     * @param right right part of pair
     */
    public void setValue(Right right) {
        this.right = right;
    }
}