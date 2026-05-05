package io.github.travon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuScreen implements Screen {

    private final Main game;

    private static final int W = 640;
    private static final int H = 480;

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private BitmapFont titleFont;
    private BitmapFont promptFont;
    private GlyphLayout layout;
    private float stateTime = 0f;

    public MenuScreen(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, W, H);

        titleFont = new BitmapFont();
        titleFont.setColor(Color.YELLOW);
        titleFont.getData().setScale(3f);

        promptFont = new BitmapFont();
        promptFont.setColor(Color.WHITE);
        promptFont.getData().setScale(1.5f);

        layout = new GlyphLayout();
    }

    @Override
    public void render(float delta) {
        stateTime += delta;

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.setScreen(new GameScreen(game));
            dispose();
            return;
        }

        Gdx.gl.glClearColor(0.05f, 0.05f, 0.1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        String title = "PLATFORMER";
        layout.setText(titleFont, title);
        titleFont.draw(batch, title, (W - layout.width) / 2f, H / 2f + 80);

        if ((int)(stateTime * 2) % 2 == 0) {
            String prompt = "Press ENTER to Start";
            layout.setText(promptFont, prompt);
            promptFont.draw(batch, prompt, (W - layout.width) / 2f, H / 2f - 20);
        }

        promptFont.getData().setScale(1f);
        String controls = "Arrow Keys: Move   |   Space: Jump";
        layout.setText(promptFont, controls);
        promptFont.draw(batch, controls, (W - layout.width) / 2f, 60);
        promptFont.getData().setScale(1.5f);

        batch.end();
    }

    @Override
    public void resize(int w, int h) { camera.setToOrtho(false, W, H); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        titleFont.dispose();
        promptFont.dispose();
    }
}
