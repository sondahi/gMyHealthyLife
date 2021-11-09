package com.comert.mhl.client.clientconnection;

import com.comert.mhl.database.service.DataBaseMessageProducerRemote;
import com.comert.mhl.database.service.MemberService;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class EJBConnection extends ClientConnection {

    private final String JNDI_PREFIX;

    //DataBase Service
    private static final String MEMBER_REPOISTORY_EJB = "MemberServiceEJB!";
    private static final String FOOD_REPOSITORY_EJB = "FoodServiceEJB!";
    private static final String FOOD_CATEGORY_REPOSITORY_EJB = "FoodCategoryServiceEJB!";
    private static final String MEAL_REPOSITORY_EJB = "MealServiceEJB!";
    private static final String MEAL_CATEGORY_REPOSITORY_EJB = "MealCategoryServiceEJB!";
    private static final String DATABASE_MESSAGE_PRODUCER_EJB = "DataBaseMessageProducerEJB!";

    //Mail Service
    private static final String MAIL_SENDER_EJB = "MailSenderEJB!";

    EJBConnection(InitialContext context, String jndiPrefix){
        super(context);
        JNDI_PREFIX = jndiPrefix;
    }

    public MemberService getMemberServiceRemote() throws NamingException {
        MemberServiceRemote remote = (MemberServiceRemote) context.lookup(JNDI_PREFIX + MEMBER_REPOISTORY_EJB + MemberServiceRemote.class.getName());
        System.out.println(remote.getClass());
        return remote;
    }

    public FoodServiceRemote getFoodServiceRemote() throws NamingException {
        return (FoodServiceRemote) context.lookup(JNDI_PREFIX + FOOD_REPOSITORY_EJB + FoodServiceRemote.class.getName());
    }



    public MealCategoryServiceRemote getMealServiceRemote() throws NamingException {
        return (MealCategoryServiceRemote) context.lookup(JNDI_PREFIX + MEAL_REPOSITORY_EJB + MealCategoryServiceRemote.class.getName());
    }


    public DataBaseMessageProducerRemote getDataBaseSynchronizerRemote() throws NamingException {
        return (DataBaseMessageProducerRemote) context.lookup(JNDI_PREFIX + DATABASE_MESSAGE_PRODUCER_EJB + DataBaseMessageProducerRemote.class.getName());
    }

}
