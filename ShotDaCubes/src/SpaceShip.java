import com.jogamp.opengl.GL2;

public class SpaceShip {

    private static final float MOVE_SPEED = 0.2f;

    private float x = 0f;

    private LaserPiouPiou shot = null;

    public void draw(GL2 gl) {
        gl.glTranslatef(x, -5.0f, 0f);
        gl.glScalef(.8f, .8f, .8f);

        gl.glBegin(GL2.GL_TRIANGLES);
        gl.glColor3f(1, 1, 1); // white

        gl.glVertex3f(-0.5f, 0.0f, 0f);  // Top-left
        gl.glVertex3f(0.5f, 0.0f, 0f);  // Top-right
        gl.glVertex3f(0.0f, 0.5f, 0f);  // Middle

        gl.glEnd();
    }

    public void move(Direction right) {
        if (right == Direction.RIGHT) {
            x += MOVE_SPEED;
        } else {
            x -= MOVE_SPEED;
        }
    }

    public float getX() {
        return x;
    }

    public void shoot(GL2 gl) {
        if (shot == null) {
            shot = new LaserPiouPiou(x, -3.0f);
        }

        shot.draw(gl);
    }

    public LaserPiouPiou getLaserPiouPiou() {
        return shot;
    }

    public void removeLaser() {
        shot = null;
    }

    public enum Direction {
        LEFT, RIGHT
    }
}
