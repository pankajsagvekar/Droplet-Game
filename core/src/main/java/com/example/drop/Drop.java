package com.example.drop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Drop extends ApplicationAdapter {
    private Texture dropImage;
    private Texture bucketImage;
    private Music rainMusic;

    private OrthographicCamera camera;
    private SpriteBatch batch; //used to draw 2D images

    private Rectangle bucket;

    @Override
    public void create() {
        //load image
        dropImage = new Texture(Gdx.files.internal("droplet.png")); 
        bucketImage = new Texture(Gdx.files.internal("bucket.png")); 

        //load music
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

        //set camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        batch = new SpriteBatch();

        bucket = new Rectangle();
        bucket.x = 800/2 - 64/2;
        bucket.y = 20;
        bucket.width = 64;
        bucket.height = 64;

        rainMusic.setLooping(true);
        rainMusic.play();
    }

    @Override
    public void render(){
        ScreenUtils.clear(0,0,0.2f, 1); //(r,g,b,a)
        camera.update(); //update camera everytime it is done using matrix that's why we are using projectionMatrix after that

        //rendering bucket
        batch.setProjectionMatrix(camera.combined);
        
        batch.begin();
            batch.draw(bucketImage, bucket.x, bucket.y);
        batch.end();
    }
}
