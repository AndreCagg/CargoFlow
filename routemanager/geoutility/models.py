from django.db import models

# Create your models here.
class Coordinate(models.Model):
    lat = models.DecimalField(max_digits = 9, decimal_places = 6)
    lon = models.DecimalField(max_digits = 9, decimal_places = 6)

    def __str__(self):
        return f"{self.lat} {self.lon}"
 