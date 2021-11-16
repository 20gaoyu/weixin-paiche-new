package cn.gy.bean;

import org.springframework.context.ApplicationEvent;

/**
 * Created by JDChen on 2019/8/2/
 */
public class NewCustAuditEvent extends ApplicationEvent {

    private TMCustomer tMCustomer;

    public NewCustAuditEvent(Object source, TMCustomer tMCustomer) {
        super(source);
        this.tMCustomer = tMCustomer;
    }

    public TMCustomer gettMCustomer() {
        return tMCustomer;
    }

    public void settMCustomer(TMCustomer tMCustomer) {
        this.tMCustomer = tMCustomer;
    }
}
