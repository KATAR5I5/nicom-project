public enum DeviceType {
    NOTEBOOK("ноутбук"),
    ACTIONCAMERA("экшен камера"),
    PROJECTOR("проектор"),
    XBOX("игровая приставка большая"),
    SMALLCONSOL("игровая приставка малая"),
    MONOBLOK("моноблок"),
    OTHER("другое 800 руб., неопределенная техника");

//    BIGCONSOLE,
//    SMALLCONSOLE,
//    SONYPLAYSTATION,
//    CAMERA,
//    COFFEEMACHINE,

//    MOBILEPHONE,
//    RADIOPHONE,
//    PHOTOCAMERA,

    private String title;

    DeviceType(String title) {
        this.title = title;
    }


    @Override
    public String toString() {
        return title;
    }

}
