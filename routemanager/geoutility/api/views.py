import requests
from urllib.parse import quote
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from .serializer import LocationsSerializer

def indirizzo2coord(indirizzo):
    if not indirizzo:
        return Response(
            {"errore": "Il parametro 'indirizzo' è obbligatorio."}, 
            status=status.HTTP_400_BAD_REQUEST
        )

    indirizzo_encoded = quote(indirizzo)
    
    url = f"https://api.tomtom.com/search/2/geocode/{indirizzo_encoded}.json?storeResult=false&view=Unified&key=Pd62ReAXUY24gPo1hUoi0PpFLNYlRbx5"
    
    api_response = requests.get(url)
    
    data = api_response.json()
    if not api_response.ok: return None

    return data["results"][0]["position"]

def distanceMatrix(coords):
    
    url = f"https://api.heigit.org/openrouteservice/v2/matrix/driving-hgv"
    
    api_response = requests.post(url, json={"locations": coords, "metrics": ["distance"]}, headers={"Authorization": "eyJvcmciOiI1YjNjZTM1OTc4NTExMTAwMDFjZjYyNDgiLCJpZCI6IjNjYzNkZGY0ZmJhMjRiZDk4NjUwYTkzYjdkNjUxZjZmIiwiaCI6Im11cm11cjY0In0="}) #coord[i] deve essere una coppia
    
    data = api_response.json()
    print(f"RESPONSE OK {api_response.ok}")
    if not api_response.ok: return None

    return data["distances"]


class ConvertiIndirizzoACoord(APIView):
    def get(self, request):
        indirizzo = request.query_params.get("indirizzo", None)

        res = indirizzo2coord(indirizzo)

        if res is None: return Response(status=status.HTTP_400_BAD_REQUEST)
            
        return Response(res, status=status.HTTP_200_OK)

class DistanceMatrix(APIView):
    def post(self, request):
        serializer = LocationsSerializer(data=request.data)

        if not serializer.is_valid(): return Response(status=status.HTTP_400_BAD_REQUEST)

        res = distanceMatrix(serializer.validated_data["locations"])

        if res is None: return Response(status=status.HTTP_400_BAD_REQUEST)
                    
        return Response(res, status=status.HTTP_200_OK)


#class RoutesPlanner(APIView):
#    def post(self, request): #prende un set di indirizzi, li trasforma in coordinate e fa ilp
