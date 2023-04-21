class Fish
{
    double x;
    double y;
    double width;
    double height;
    double velocityY;
    
    Fish(double x, double y)
    {
        this.x = x;
        this.y = y;
        
        this.width = 40;
        this.height = 30;
        this.velocityY = 0;
    }
    
    
    void jump()
    {
        velocityY -= 2;
    }
}