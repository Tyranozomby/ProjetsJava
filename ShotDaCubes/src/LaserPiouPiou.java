import com.jogamp.opengl.GL2;

public class LaserPiouPiou {

    private static final float MOVE_SPEED = 0.2f;

    private final float x;

    private float y;

    public LaserPiouPiou(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void draw(GL2 gl) {
        gl.glTranslatef(x, y, 0f);
        gl.glScalef(.2f, .5f, .2f);

        gl.glColor3f(1, 0, 0); // red

        gl.glBegin(GL2.GL_QUADS);

        // Top face
        gl.glVertex3f(-0.5f, 1.0f, 0.5f);  // Top-left
        gl.glVertex3f(0.5f, 1.0f, 0.5f);  // Top-right
        gl.glVertex3f(0.5f, -1.0f, 0.5f);  // Bottom-right
        gl.glVertex3f(-0.5f, -1.0f, 0.5f);  // Bottom-left

        // Bottom face
        gl.glVertex3f(-0.5f, 1.0f, -0.5f);  // Top-left
        gl.glVertex3f(0.5f, 1.0f, -0.5f);  // Top-right
        gl.glVertex3f(0.5f, -1.0f, -0.5f);  // Bottom-right
        gl.glVertex3f(-0.5f, -1.0f, -0.5f);  // Bottom-left

        // Front face
        gl.glVertex3f(-0.5f, 1.0f, -0.5f);  // Top-left
        gl.glVertex3f(0.5f, 1.0f, -0.5f);  // Top-right
        gl.glVertex3f(0.5f, 1.0f, 0.5f);  // Bottom-right
        gl.glVertex3f(-0.5f, 1.0f, 0.5f);  // Bottom-left

        // Back face
        gl.glVertex3f(-0.5f, -1.0f, -0.5f);  // Top-left
        gl.glVertex3f(0.5f, -1.0f, -0.5f);  // Top-right
        gl.glVertex3f(0.5f, -1.0f, 0.5f);  // Bottom-right
        gl.glVertex3f(-0.5f, -1.0f, 0.5f);  // Bottom-left

        // Left face
        gl.glVertex3f(-0.5f, 1.0f, 0.5f);  // Top-left
        gl.glVertex3f(-0.5f, 1.0f, -0.5f);  // Top-right
        gl.glVertex3f(-0.5f, -1.0f, -0.5f);  // Bottom-right
        gl.glVertex3f(-0.5f, -1.0f, 0.5f);  // Bottom-left

        // Right face
        gl.glVertex3f(0.5f, 1.0f, 0.5f);  // Top-left
        gl.glVertex3f(0.5f, 1.0f, -0.5f);  // Top-right
        gl.glVertex3f(0.5f, -1.0f, -0.5f);  // Bottom-right
        gl.glVertex3f(0.5f, -1.0f, 0.5f);  // Bottom-left

        gl.glEnd();
    }

    public void move() {
        y += MOVE_SPEED;
    }

    public float getY() {
        return y;
    }

    public boolean collidesWith(Cube cube) {
        return x >= cube.getX() - 0.5f + cube.getOffset() && x <= cube.getX() + 0.5f + cube.getOffset()
                && y >= cube.getY() - 0.5f && y <= cube.getY() + 0.5f;
    }
}
