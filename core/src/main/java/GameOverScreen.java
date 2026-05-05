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

public class GameOverScreen implements Screen {

    private final Main game;
    private final int finalScore;
    private final boolean won;

    private static final int W = 640;
    private static final int H = 480;

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private BitmapFont titleFont;
    private BitmapFont bodyFont;
    private GlyphLayout layout;
    private float stateTime = 0f;

    public GameOverScreen(Main game, int finalScore, boolean won) {
        this.game = game;
        this.finalScore = finalScore;
        this.won = won;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, W, H);

        titleFont = new BitmapFont();
        titleFont.getData().setScale(3f);

        bodyFont = new BitmapFont();
        bodyFont.setColor(Color.WHITE);
        bodyFont.getData().setScale(1.5f);

        layout = new GlyphLayout();
    }

    @Override
    public void render(float delta) {
        stateTime += delta;

        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            game.setScreen(new GameScreen(game));
            dispose();
            return;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            game.setScreen(new MenuScreen(game));
            dispose();
            return;
        }

        Gdx.gl.glClearColor(0.05f, 0.05f, 0.1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        String title = won ? "YOU WIN!" : "GAME OVER";
        titleFont.setColor(won ? Color.YELLOW : Color.RED);
        layout.setText(titleFont, title);
        titleFont.draw(batch, title, (W - layout.width) / 2f, H / 2f + 80);

        String scoreText = "Final Score: " + finalScore;
        layout.setText(bodyFont, scoreText);
        bodyFont.draw(batch, scoreText, (W - layout.width) / 2f, H / 2f);

        if ((int)(stateTime * 2) % 2 == 0) {
            String restart = "Press R to Play Again";
            layout.setText(bodyFont, restart);
            bodyFont.draw(batch, restart, (W - layout.width) / 2f, H / 2f - 60);
        }

        bodyFont.getData().setScale(1.2f);
        String menu = "Press M for Menu";
        layout.setText(bodyFont, menu);
        bodyFont.draw(batch, menu, (W - layout.width) / 2f, H / 2f - 110);
        bodyFont.getData().setScale(1.5f);

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
        bodyFont.dispose();
    }
}
