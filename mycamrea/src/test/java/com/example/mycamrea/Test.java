package com.example.mycamrea;



class TicketsSale {
    public boolean sale(){
        if (Test2.tickets>0){
            synchronized (this)
            {
                Test2.tickets--;
            }

//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            return true;
        }else return false;
    }
}
