package com.example.drop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Drop extends ApplicationAdapter {
    private Texture dropImage;
    private Texture bucketImage;
    private Music rainMusic;

    @Override
    public void create() {
        //load image
        dropImage = new Texture(Gdx.files.internal("droplet.png")); 
        bucketImage = new Texture(Gdx.files.internal("bucket.png")); 

        //load music
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

        rainMusic.setLooping(true);
        rainMusic.play();
    } 
}
