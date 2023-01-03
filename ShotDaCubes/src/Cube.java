import com.jogamp.opengl.GL2;

public class Cube {

    private static final float ROTATION_SPEED = 3f;

    private final float x;

    private final float y;

    private float rotate = 0f;

    private float offset = 0f;

    public Cube(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void draw(GL2 gl) {
        // Rotate the cube
        gl.glTranslatef(x + offset, y, 0.0f);
        gl.glScalef(.8f, .8f, .8f);
        gl.glRotatef(rotate, 0.0f, 1.0f, 0.0f);

        // Draw the front face
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex3f(-0.5f, -0.5f, 0.5f);  // Bottom-left
        gl.glVertex3f(0.5f, -0.5f, 0.5f);  // Bottom-right
        gl.glVertex3f(0.5f, 0.5f, 0.5f);  // Top-right
        gl.glVertex3f(-0.5f, 0.5f, 0.5f);  // Top-left

        // Draw the back face
        gl.glVertex3f(-0.5f, -0.5f, -0.5f);  // Bottom-left
        gl.glVertex3f(0.5f, -0.5f, -0.5f);  // Bottom-right
        gl.glVertex3f(0.5f, 0.5f, -0.5f);  // Top-right
        gl.glVertex3f(-0.5f, 0.5f, -0.5f);  // Top-left

        // Draw the bottom face
        gl.glVertex3f(-0.5f, -0.5f, 0.5f);  // Bottom-left
        gl.glVertex3f(0.5f, -0.5f, 0.5f);  // Bottom-right
        gl.glVertex3f(0.5f, -0.5f, -0.5f);  // Top-right
        gl.glVertex3f(-0.5f, -0.5f, -0.5f);  // Top-left

        // Draw the top face
        gl.glVertex3f(-0.5f, 0.5f, 0.5f);  // Bottom-left
        gl.glVertex3f(0.5f, 0.5f, 0.5f);  // Bottom-right
        gl.glVertex3f(0.5f, 0.5f, -0.5f);  // Top-right
        gl.glVertex3f(-0.5f, 0.5f, -0.5f);  // Top-left

        // Draw the left face
        gl.glVertex3f(-0.5f, -0.5f, 0.5f);  // Bottom-left
        gl.glVertex3f(-0.5f, 0.5f, 0.5f);  // Bottom-right
        gl.glVertex3f(-0.5f, 0.5f, -0.5f);  // Top-right
        gl.glVertex3f(-0.5f, -0.5f, -0.5f);  // Top-left

        // Draw the right face
        gl.glVertex3f(0.5f, -0.5f, 0.5f);  // Bottom-left
        gl.glVertex3f(0.5f, 0.5f, 0.5f);  // Bottom-right
        gl.glVertex3f(0.5f, 0.5f, -0.5f);  // Top-right
        gl.glVertex3f(0.5f, -0.5f, -0.5f);  // Top-left
        gl.glEnd();
    }

    public float getOffset() {
        return offset;
    }

    public void setOffset(float offset) {
        this.offset = offset;
    }

    public void rotate(int xDir) {
        rotate += xDir * ROTATION_SPEED;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
