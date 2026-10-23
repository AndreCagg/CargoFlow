from rest_framework import serializers
from geoutility.models import Coordinate

class CoordinateSerializer(serializers.ModelSerializer):
    class Meta:
        model = Coordinate
        fields = "__all__"

class LocationsSerializer(serializers.Serializer):
    locations = serializers.ListField(
        child = serializers.ListField(
            child = serializers.FloatField(),
            min_length = 2,
            max_length = 2
        )
    )