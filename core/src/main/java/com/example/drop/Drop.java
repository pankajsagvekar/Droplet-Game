package com.example.drop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
public class Drop extends ApplicationAdapter {
    private Texture dropImage;
    private Texture bucketImage;
    private Music rainMusic;

    private OrthographicCamera camera;
    private SpriteBatch batch; // used to draw 2D images

    private Rectangle bucket;

    private Array<Rectangle> raindrops;
    private long lastDropTime; // track of the last time we spawned a raindrop

    @Override
    public void create() {
        // load image
        dropImage = new Texture(Gdx.files.internal("drop.png"));
        bucketImage = new Texture(Gdx.files.internal("bucket.png"));

        // load music
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

        // set camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        batch = new SpriteBatch();

        // create bucket
        bucket = new Rectangle();
        bucket.x = 800 / 2 - 64 / 2;
        bucket.y = 20;
        bucket.width = 64;
        bucket.height = 64;

        // create raindrop
        raindrops = new Array<Rectangle>();
        spawnRaindrop();

        rainMusic.setLooping(true);
        rainMusic.play();
    }

    private void spawnRaindrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800 - 64); // random x value
        raindrop.y = 480;
        raindrop.width = 64;
        raindrop.height = 64;

        raindrops.add(raindrop); // add to the arrray
        lastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0.2f, 1); // (r,g,b,a)
        camera.update(); // update camera everytime it is done using matrix that's why we are using
                         // projectionMatrix after that

        // rendering bucket
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
            batch.draw(bucketImage, bucket.x, bucket.y);
            for(Rectangle raindrop: raindrops){
                batch.draw(dropImage, raindrop.x, raindrop.y);
            }
        batch.end();

        // mouse input
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0); // return mouse position
            camera.unproject(touchPos); // To transform these coordinates to our camera’s coordinate system, we need to
                                        // call this method
            bucket.x = touchPos.x - 64 / 2; // follow mouse on x
        }

        // keyboard input
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            bucket.x -= 200 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            bucket.x += 200 * Gdx.graphics.getDeltaTime();
        }

        // get bucket to stay within screen limit
        if (bucket.x < 0)
            bucket.x = 0;
        if (bucket.x > 800 - 64)
            bucket.x = 800 - 64;

        // check how much time has passed since we spawned a new raindrop, and creates a
        // new one if necessary
        if (TimeUtils.nanoTime() - lastDropTime > 1000000000) {
            spawnRaindrop();
        }

        //If the raindrop is beneath the bottom edge of the screen, we remove it from the array.
        for(int i = 0; i < raindrops.size; i++){
            Rectangle raindrop = raindrops.get(i);
            raindrop.y -= 200 * Gdx.graphics.getDeltaTime();

            if(raindrop.y + 64 < 0){
                raindrops.removeIndex(i); //remove drop after touching screen
                i--; // Adjust index after removal
            }

            if(raindrop.overlaps(bucket)) {
                raindrops.removeIndex(i); //remove drop after touching BUCKET
                i--; // Adjust index after removal
            }

        }
    }

}
