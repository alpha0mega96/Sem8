
import com.mongodb.*;

class Philosopher implements Runnable {
  private final Object left;
  private final Object right;
  int tid;
  private int threadapp;
  Dinner obj;
  DBCollection coll;
  BasicDBObject query;
  BasicDBObject inckey;
  DBCursor cursor;
  
  public Philosopher(Object left, Object right,int id,Dinner obj) {
    this.left = left;
    this.right = right;
    this.tid=id;
    this.obj=obj;
    this.threadapp=0;
    MongoClient cli=new MongoClient("localhost",27017);
    DB db= cli.getDB("DiningPhilo");
    coll=db.getCollection("apples");
    
    inckey=new BasicDBObject();
    query=new BasicDBObject();
  }
 
  public void setphiloapplecount(int app,int tid)
 {
     //cursor=coll.find(query, inckey);
     //while(cursor.hasNext())
     //{
     //    DBObject o=cursor.next();
         //threadapp=threadapp+1;
         query.put("_id",tid+1);
         inckey.put("apples",threadapp);     
         coll.update(query, inckey);
         System.out.println("Successfully eaten :" + tid + " apple count "+ threadapp);
     //}
 }
  
  private void ponder() throws InterruptedException {
    Thread.sleep(1000);
  }
  public void run() {
    try {
      while (obj.totalapples>0) {
        ponder(); // thinking
        System.out.println("Philosopher "+ tid+" is thinking");
        synchronized (left) {
          ponder();
        System.out.println("Philosopher "+ tid+" is hungry");          
          synchronized (right) {
            ponder(); // eating            
            if(obj.totalapples>0){
            threadapp=threadapp+1;
            System.out.println("Philosopher : "+tid+" is eating");
            setphiloapplecount(threadapp,tid);
            obj.settotalapples(obj.totalapples-1);
            System.out.println(" TOTAL APPLES :"+ obj.totalapples);
                        
            }
            else{
                //System.out.println("Resources over. Deadlock has occured");
                break;
            }
            }
        }
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      return;
    }
  }
 }

public class Dinner {

 int totalapples;
 public void settotalapples(int app)
 {
     totalapples=app;    
 }

 public static void main(String[] args) throws Exception {
               final Philosopher[] philosophers = new Philosopher[5];
              Object[] chopsticks = new Object[philosophers.length];
              Dinner obj=new Dinner();
              obj.settotalapples(50);
              for (int i = 0; i < chopsticks.length; i++) {
                      chopsticks[i] = new Object();
              }
        for (int i = 0; i < philosophers.length; i++) {
              Object left = chopsticks[i];
              Object right = chopsticks[(i + 1) % chopsticks.length];
              if(i==0){
                   philosophers[i] = new Philosopher(right, left,i,obj);
              }else{
                   philosophers[i] = new Philosopher(left, right,i,obj);
              }
              //obj.settotalapples(obj.totalapples-1);
              Thread t = new Thread(philosophers[i], "Phil " + (i + 1));
              t.start();              
     }
  }
}



