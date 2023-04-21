class Obstacle
{
    double x;
    double y;
    double width;
    double height;
    
    Obstacle(double x, double y, double width, double height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    boolean collidesWith(Fish player)
    {
        // Check if the center of the fish is inside the obstacle.
        
        double fishCenterX = player.x + player.width/2;
        double fishCenterY = player.y + player.height/2;
        
        boolean horizontallyAligned = (this.x < fishCenterX) && (fishCenterX < this.x + this.width);
        boolean verticallyAligned = (this.y < fishCenterY) && (fishCenterY < this.y + this.height);
        
        return horizontallyAligned && verticallyAligned;
    }
    
}