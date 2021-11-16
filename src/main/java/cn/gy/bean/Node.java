package cn.gy.bean;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;

/**
 * @author kuyun
 * @date 2018-07-30
 * @description 菜单层级数据结构
 */
@Slf4j
public class Node<T> {
    private T content;
    private List<Node<T>> children = new LinkedList<>();

    public Node(T content) {
        this.content = content;
    }

    public void addChild(Node<T> node) {
        this.getChildren().add(node);
    }

    public void clearChild() {
        this.getChildren().clear();
    }

    public boolean removeChild(int i) {
        try {
            this.getChildren().remove(i);
        } catch (Exception e) {
            log.info("context",e);
            return false;
        }

        return true;
    }

    /**
     * @return the content
     */
    public T getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(T content) {
        this.content = content;
    }

    /**
     * @return the children
     */
    public List<Node<T>> getChildren() {
        return children;
    }

    /**
     * @param children the children to set
     */
    public void setChildren(List<Node<T>> children) {
        this.children = children;
    }
}
