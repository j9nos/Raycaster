import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public final class Raycaster extends JPanel {

    private final Color
		sky = new Color(23, 0, 44),
		wall = new Color(187, 0, 0),
		ground = new Color(43, 175, 0);
    private final double renderingPrecision = .01d;

    private Raycaster() {
        setPreferredSize(new Dimension(900, 350));
        addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(final KeyEvent event) {

            }

            @Override
            public void keyPressed(final KeyEvent event) {
                switch (event.getKeyCode()) {
                    case KeyEvent.VK_W -> Player.sendUpSignal(true);
                    case KeyEvent.VK_A -> Player.sendLeftSignal(true);
                    case KeyEvent.VK_S -> Player.sendDownSignal(true);
                    case KeyEvent.VK_D -> Player.sendRightSignal(true);
                }
            }

            @Override
            public void keyReleased(final KeyEvent event) {
                switch (event.getKeyCode()) {
                    case KeyEvent.VK_W -> Player.sendUpSignal(false);
                    case KeyEvent.VK_A -> Player.sendLeftSignal(false);
                    case KeyEvent.VK_S -> Player.sendDownSignal(false);
                    case KeyEvent.VK_D -> Player.sendRightSignal(false);
                }
            }
        });
        setFocusable(true);
        final JFrame frame = new JFrame("Raycaster");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(final String... args) {
        final Raycaster raycaster = new Raycaster();
        Executors
                .newSingleThreadScheduledExecutor()
                .scheduleAtFixedRate(
                        raycaster::loop,
                        0,
                        16,
                        TimeUnit.MILLISECONDS);
    }

    @Override
    protected void paintComponent(final Graphics graphics) {
        super.paintComponent(graphics);
        graphics.clearRect(0, 0, getWidth(), getHeight());
        for (int rayIndex = 0; rayIndex < getWidth(); rayIndex++) {
            final double rayAngle = Player.getAngle() + Player.getFieldOfViewHalf()
                    - Player.getFieldOfView() / getWidth() * rayIndex;
            final double radians = Math.toRadians(rayAngle);
            final double cos = Math.cos(radians) * renderingPrecision;
            final double sin = Math.sin(radians) * renderingPrecision;

            double rayX = Player.getX();
            double rayY = Player.getY();
            while (Room.isVoid(rayX, rayY)) {
                rayX += cos;
                rayY -= sin;
            }

            final double distanceX = Player.getX() - rayX;
            final double distanceY = Player.getY() - rayY;
            final double pythagoreanDistance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);
            final double distance = pythagoreanDistance * Math.cos(Math.toRadians(rayAngle - Player.getAngle()));

            final double stripeLength = getHeight() / distance;
            final double halfHeight = getHeight() * .5d;
            final int stripeTop = (int) (halfHeight - stripeLength);
            final int stripeBottom = (int) (halfHeight + stripeLength);

            graphics.setColor(sky);
            graphics.drawLine(rayIndex, 0, rayIndex, stripeTop);
            graphics.setColor(wall);
            graphics.drawLine(rayIndex, stripeTop, rayIndex, stripeBottom);
            graphics.setColor(ground);
            graphics.drawLine(rayIndex, stripeBottom, rayIndex, getHeight());
        }
        graphics.dispose();
    }

    private void loop() {
        Player.update();
        repaint();
    }

}
