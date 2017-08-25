package zhaoyaohealthy;


import java.util.List;

/**
 * Created by liuwei on 2017/8/3 15:29
 */

public class Drug {

    /**
     * couponid : 0
     * selluserid : 3
     * payment : 2400.00
     * paymenttype : 2
     * buyermessage :
     * buyernick :
     * endaddressid : 40
     * endaddr : 胡佰呈=四川省-成都市-双流县西航港科技企业孵化中心307室、=13281165126=610000
     * details : [{"num":100,"imagePath":"http://7xloj2.com1.z0.glb.clouddn.com/1482733218549 ","drugname":"甲硝唑阴道泡腾片","drugid":42,"price":25.17,"actcontent":"秒杀商品,秒杀单价￥24","totalfee":2400,
     * "actid":"94","acttype":"3","actlimt":"200","actmes":"24.0","actstate":"1","standard":2}]
     * hasaftersale : 0
     */

    private int couponid;
    private int selluserid;
    private String payment;
    private int paymenttype;
    private String buyermessage;
    private String buyernick;
    private int endaddressid;
    private String endaddr;
    private int hasaftersale;
    private List<DetailsBean> details;

    public int getCouponid() {
        return couponid;
    }

    public void setCouponid(int couponid) {
        this.couponid = couponid;
    }

    public int getSelluserid() {
        return selluserid;
    }

    public void setSelluserid(int selluserid) {
        this.selluserid = selluserid;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public int getPaymenttype() {
        return paymenttype;
    }

    public void setPaymenttype(int paymenttype) {
        this.paymenttype = paymenttype;
    }

    public String getBuyermessage() {
        return buyermessage;
    }

    public void setBuyermessage(String buyermessage) {
        this.buyermessage = buyermessage;
    }

    public String getBuyernick() {
        return buyernick;
    }

    public void setBuyernick(String buyernick) {
        this.buyernick = buyernick;
    }

    public int getEndaddressid() {
        return endaddressid;
    }

    public void setEndaddressid(int endaddressid) {
        this.endaddressid = endaddressid;
    }

    public String getEndaddr() {
        return endaddr;
    }

    public void setEndaddr(String endaddr) {
        this.endaddr = endaddr;
    }

    public int getHasaftersale() {
        return hasaftersale;
    }

    public void setHasaftersale(int hasaftersale) {
        this.hasaftersale = hasaftersale;
    }

    public List<DetailsBean> getDetails() {
        return details;
    }

    public void setDetails(List<DetailsBean> details) {
        this.details = details;
    }

    public static class DetailsBean {
        /**
         * num : 100
         * imagePath : http://7xloj2.com1.z0.glb.clouddn.com/1482733218549
         * drugname : 甲硝唑阴道泡腾片
         * drugid : 42
         * price : 25.17
         * actcontent : 秒杀商品,秒杀单价￥24
         * totalfee : 2400
         * actid : 94
         * acttype : 3
         * actlimt : 200
         * actmes : 24.0
         * actstate : 1
         * standard : 2
         */

        private int num;
        private String imagePath;
        private String drugname;
        private int drugid;
        private double price;
        private String actcontent;
        private int totalfee;
        private String actid;
        private String acttype;
        private String actlimt;
        private String actmes;
        private String actstate;
        private int standard;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getImagePath() {
            return imagePath;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }

        public String getDrugname() {
            return drugname;
        }

        public void setDrugname(String drugname) {
            this.drugname = drugname;
        }

        public int getDrugid() {
            return drugid;
        }

        public void setDrugid(int drugid) {
            this.drugid = drugid;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getActcontent() {
            return actcontent;
        }

        public void setActcontent(String actcontent) {
            this.actcontent = actcontent;
        }

        public int getTotalfee() {
            return totalfee;
        }

        public void setTotalfee(int totalfee) {
            this.totalfee = totalfee;
        }

        public String getActid() {
            return actid;
        }

        public void setActid(String actid) {
            this.actid = actid;
        }

        public String getActtype() {
            return acttype;
        }

        public void setActtype(String acttype) {
            this.acttype = acttype;
        }

        public String getActlimt() {
            return actlimt;
        }

        public void setActlimt(String actlimt) {
            this.actlimt = actlimt;
        }

        public String getActmes() {
            return actmes;
        }

        public void setActmes(String actmes) {
            this.actmes = actmes;
        }

        public String getActstate() {
            return actstate;
        }

        public void setActstate(String actstate) {
            this.actstate = actstate;
        }

        public int getStandard() {
            return standard;
        }

        public void setStandard(int standard) {
            this.standard = standard;
        }

        @Override
        public String toString() {
            return "DetailsBean{" +
                    "num=" + num +
                    ", imagePath='" + imagePath + '\'' +
                    ", drugname='" + drugname + '\'' +
                    ", drugid=" + drugid +
                    ", price=" + price +
                    ", actcontent='" + actcontent + '\'' +
                    ", totalfee=" + totalfee +
                    ", actid='" + actid + '\'' +
                    ", acttype='" + acttype + '\'' +
                    ", actlimt='" + actlimt + '\'' +
                    ", actmes='" + actmes + '\'' +
                    ", actstate='" + actstate + '\'' +
                    ", standard=" + standard +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Drug{" +
                "couponid=" + couponid +
                ", selluserid=" + selluserid +
                ", payment='" + payment + '\'' +
                ", paymenttype=" + paymenttype +
                ", buyermessage='" + buyermessage + '\'' +
                ", buyernick='" + buyernick + '\'' +
                ", endaddressid=" + endaddressid +
                ", endaddr='" + endaddr + '\'' +
                ", hasaftersale=" + hasaftersale +
                ", details=" + details +
                '}';
    }
}
