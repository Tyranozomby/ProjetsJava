import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.awt.TextRenderer;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class StartGame extends GLCanvas implements GLEventListener, KeyListener {

    private final ArrayList<Cube> cubes = new ArrayList<>();

    private final SpaceShip spaceShip = new SpaceShip();

    private float xOffset = -8f;

    private int xDir = 1;

    private boolean left = false;

    private boolean right = false;

    private boolean shoot = false;

    public StartGame() {
        this.setPreferredSize(new Dimension(1080, 720));

        JFrame frame = new JFrame("OpenGL ShotDaCubes");
        frame.getContentPane().add(this);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        frame.addKeyListener(this);

        new FPSAnimator(this, 300, true).start();
        addGLEventListener(this);
    }

    public static void main(String[] args) {
        new StartGame();
    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        // vider les valeurs du z-buffer
        gl.glClearDepth(1.0f);
        // activer le test de profondeur
        gl.glEnable(GL2.GL_DEPTH_TEST);
        // choisir le type de test de profondeur
        gl.glDepthFunc(GL2.GL_LEQUAL);
        // choix de la meilleure correction de perspective
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);


        // 2 rows of 6 cubes spaced by 2 units
        int rows = 2;
        int cols = 6;
        float space = 2.0f;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                cubes.add(new Cube(j * space, 3f + i * space));
            }
        }
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();

        // Clear the screen
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

        gl.glLoadIdentity();
        gl.glTranslatef(0, 0, -15f);

        gl.glColor3f(1, 1, 0);

        if (cubes.isEmpty()) {
            TextRenderer renderer = new TextRenderer(new Font("Monospaced", Font.BOLD, 40));

            String text = "Vous avez gagné ! :D";
            Rectangle2D bounds = renderer.getBounds(text);

            int x = (1080 - (int) bounds.getWidth()) / 2;
            int y = (720 - (int) bounds.getHeight()) / 2;

            renderer.beginRendering(1080, 720);
            renderer.draw(text, x, y);
            renderer.endRendering();

            return;
        }

        for (Cube cube : cubes) {

            gl.glPushMatrix();
            {
                cube.draw(gl);
                cube.rotate(xDir);
                cube.setOffset(xOffset);
            }
            gl.glPopMatrix();
        }

        xOffset += .05f * xDir;
        if (xOffset > -1.5f) {
            xDir = -1;
        } else if (xOffset < -8f) {
            xDir = 1;
        }

        gl.glPushMatrix();
        {
            spaceShip.draw(gl);
            if (left && !right && spaceShip.getX() > -8.5f) {
                spaceShip.move(SpaceShip.Direction.LEFT);
            } else if (right && !left && spaceShip.getX() < 8.5f) {
                spaceShip.move(SpaceShip.Direction.RIGHT);
            }
        }
        gl.glPopMatrix();

        gl.glPushMatrix();
        {
            LaserPiouPiou laser = spaceShip.getLaserPiouPiou();

            if (shoot && laser == null) {
                spaceShip.shoot(gl);
            } else if (laser != null) {
                laser.draw(gl);
                laser.move();

                for (int i = 0; i < cubes.size(); i++) {
                    Cube cube = cubes.get(i);
                    if (laser.collidesWith(cube)) {
                        cubes.remove(i);
                        spaceShip.removeLaser();
                        break;
                    }
                }

                if (laser.getY() > 5f) {
                    spaceShip.removeLaser();
                }
            }
        }
        gl.glPopMatrix();

        // Flush the pipeline
        gl.glFlush();
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width, int height) {
        GL2 gl = glAutoDrawable.getGL().getGL2();
        GLU glu = GLU.createGLU(gl);

        float aspect = (float) width / height;
        // Set the view port (display area)
        gl.glViewport(0, 0, width, height);
        // Setup perspective projection,
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0, aspect, 0.1, 100.0);
        // Enable the model-view transform
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity(); // reset
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            left = true;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            right = true;
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            shoot = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            left = false;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            right = false;
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            shoot = false;
        }
    }
}
