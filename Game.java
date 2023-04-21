class Game
{
    Fish player;
    int points;
    int ticks;

    Obstacle[] obstacles;

    double width;
    double height;

    boolean isGameOver;
    
    Game()
    {
        width = 1000;
        height = 600;

        player = new Fish(100, height/2); 

        ticks = 0;
        points = 0;

        // Code to randomly generate obstacles
        obstacles = new Obstacle[8];
        for (int i = 0; i < obstacles.length; i++)
        {
            double offset = i * 300;

            obstacles[i] = new Shark(width/2 + offset, Math.random() * height);
        }

        isGameOver = false;
    }
    
    void update()
    {
        if (isGameOver == true)
        {
            return;
        }

        
        updatePlayer();
        moveObstacles();
        checkCollision();
        awardPoints();
    }
    
    void updatePlayer()
    {
        // gravity
        player.y += player.velocityY;
        player.velocityY += 0.03;

        // Check if player hits the bottom
        if (player.y  > this.height - player.height || player.y < 0)
        {
            isGameOver = true;
        }
    }
    
    void moveObstacles()
    {
        for (Obstacle o : obstacles)
        {
            o.x -= 8;

            if (o.x < -100)
            {
                o.x = width + 300;
                o.y = Math.random() * height;
            }
        }
    }

    void checkCollision()
    {
        for (Obstacle o : obstacles)
        {
            if (o.collidesWith(player))
            {
                isGameOver = true;
            }
        }
    }
    
    void awardPoints()
    {
        ticks++;
        
        if (ticks >= 10)
        {
            ticks = 0;
            points++;
        }
    }
    
}