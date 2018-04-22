package entities;

import main.Game;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LivingBackground {

    private final static int DENSITY_MULTIPLIER = 25;

    private final List<Star> stars;
    private final Random random;
    private final double maxRad;

    public LivingBackground(double density, double maxRad)
    {
        this.maxRad = maxRad;
        random = new Random();
        stars = new ArrayList<>();

        // create some initial stars
        for(int i = 0; i < density * DENSITY_MULTIPLIER; i++)
        {
            stars.add(new Star(new Point(random.nextInt(Game.WIDTH), random.nextInt(Game.HEIGHT)), random.nextDouble() * maxRad,
                    random.nextDouble(), random.nextDouble() * Star.MAX_SPEED));
        }
    }

    public void update()
    {
        for(int i = 0; i < stars.size(); i++)
        {
            Star s = stars.get(i);
            s.move(0, s.getSpeed());
            if(s.getY() + s.getRadius() / 2 > Game.HEIGHT)
            {
                // replace the star if it's outside the screen
                stars.remove(s);
                stars.add(new Star(new Point(random.nextInt(Game.WIDTH), -10), random.nextDouble() * maxRad,
                        random.nextDouble(), random.nextDouble() * Star.MAX_SPEED + 3));

                System.out.println((int)s.getX()+" "+ (int)s.getY()+" "+ (int)s.getRadius());
            }
        }
    }

    public void render(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        for(Star s : stars)
        {
            g2d.setColor(new Color(255,255,255, s.getAlpha()));
            g2d.fillOval((int)s.getX(), (int)s.getY(), (int)s.getRadius(), (int)s.getRadius());
        }
    }

    private class Star{
        public final static int MAX_SPEED = 5;
        private final Point point;
        private final double radius;
        private final double alpha;
        private final double speed;

        public Star(final Point point, final double radius, final double alpha, final double speed)
        {
            this.point = point;
            this.radius = radius;
            this.alpha = alpha;
            this.speed = speed;
        }

        public void move(double dx, double dy)
        {
            point.setLocation(point.getX() + dx, point.getY() + dy);
        }

        public double getSpeed(){
            return speed;
        }

        public double getY()
        {
            return point.getY();
        }

        public double getX()
        {
            return point.getX();
        }

        public double getRadius()
        {
            return radius;
        }

        public int getAlpha()
        {
            return (int)Math.round(alpha * 255);
        }
    }
}
