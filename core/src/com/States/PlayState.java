package com.States;

import com.ali.game.FlappyBird;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.sprites.Bird;
import com.sprites.Tube;

public class PlayState extends State {
    private static final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 4;
    private static final int GROUND_Y_OFFSET = -80;
    private Texture ground;
    private Vector2 gorundPos1, groundPos2;

    private Bird bird;
    private Texture bg;
    private Sound slap;

    private Array<Tube> tubes;

    protected PlayState(GameStateManager gsm) {
        super(gsm);
        bird = new Bird(50,200);
        cam.setToOrtho(false, FlappyBird.WIDTH /2, FlappyBird.HEIGHT / 2);
        bg = new Texture("bg.png");
        ground = new Texture("ground.png");
        slap = Gdx.audio.newSound(Gdx.files.internal("slap.wav"));
        gorundPos1 = new Vector2(cam.position.x - cam.viewportWidth / 2, GROUND_Y_OFFSET);
        groundPos2 = new Vector2((cam.position.x - cam.viewportWidth / 2) + ground.getWidth(), GROUND_Y_OFFSET);


        tubes = new Array<Tube>();

        for(int i = 1; i <= TUBE_COUNT; i++){
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
        }
        // eğer istersek bu kodlarla kamera ölceklendirmesi yapabilirizi yorumu kaldıracaksan 29.Satırdakini de kaldır
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            bird.jump();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        updateGround();
        bird.update(dt);
        cam.position.x = bird.getPosition().x + 80;

        for(int i = 0; i < tubes.size; i++){
            Tube tube = tubes.get(i);
            if(cam.position.x - (cam.viewportWidth / 2) > tube.getPosTopTube().x + tube.getTopTube().getWidth()){
                tube.repozition(tube.getPosTopTube().x + ((tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
            }
            if(tube.collides(bird.getBounds())){
                slap.play(0.4f);
                gsm.set(new PlayState(gsm));
            }
        }

        if(bird.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET){
            slap.play(0.4f);
            gsm.set(new PlayState(gsm));
        }

        cam.update();

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg,cam.position.x - (cam.viewportWidth/2), 0);
        sb.draw(bird.getBird(), bird.getPosition().x, bird.getPosition().y);
        for(Tube tube : tubes){
            sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
        }
        sb.draw(ground, gorundPos1.x, gorundPos1.y);
        sb.draw(ground, groundPos2.x, groundPos2.y);
        sb.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        bird.dispose();
        ground.dispose();
        for(Tube tube : tubes){
            tube.dispose();
            System.out.println("Play State is dispose");
        }
    }

    private void updateGround(){
        if(cam.position.x - cam.viewportWidth / 2 > gorundPos1.x + ground.getWidth()){
            gorundPos1.add(ground.getWidth() * 2, 0);
        }
        if(cam.position.x - cam.viewportWidth / 2 > groundPos2.x + ground.getWidth()){
            groundPos2.add(ground.getWidth() * 2, 0);
        }
    }
}
