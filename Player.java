public final class Player {

    private static final double FIELD_OF_VIEW = 70d,
            FIELD_OF_VIEW_HALF = FIELD_OF_VIEW * .5d,
            CIRCLE = 360d,
            TORQUE = 5d,
            MOVEMENT_PRECISION = .1d;
    private static double x = 7d,
            y = 8d,
            angle = 115d;
    private static boolean upSignal,
            leftSignal,
            downSignal,
            rightSignal;

    private Player() {

    }

    public static double getFieldOfView() {
        return FIELD_OF_VIEW;
    }

    public static double getFieldOfViewHalf() {
        return FIELD_OF_VIEW_HALF;
    }

    public static double getX() {
        return x;
    }

    public static double getY() {
        return y;
    }

    public static double getAngle() {
        return angle;
    }

    public static void sendUpSignal(final boolean signal) {
        upSignal = signal;
    }

    public static void sendLeftSignal(final boolean signal) {
        leftSignal = signal;
    }

    public static void sendDownSignal(final boolean signal) {
        downSignal = signal;
    }

    public static void sendRightSignal(final boolean signal) {
        rightSignal = signal;
    }

    public static void update() {
        if (upSignal) move(true);
        if (leftSignal) rotate(true);
        if (downSignal) move(false);
        if (rightSignal) rotate(false);
    }

    private static void rotate(final boolean positive) {
        angle = (angle + (positive ? TORQUE : -TORQUE)) % CIRCLE;
    }

    private static void move(final boolean positive) {
        final double radians = Math.toRadians(angle);
        final double cos = Math.cos(radians) * MOVEMENT_PRECISION;
        final double sin = Math.sin(radians) * MOVEMENT_PRECISION;

        final double x2 = x + (positive ? cos : -cos);
        final double y2 = y + (positive ? -sin : sin);
        x = Room.isVoid(x2, y) ? x2 : x;
        y = Room.isVoid(x, y2) ? y2 : y;
    }

}
