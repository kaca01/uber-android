package com.example.ubermobileapp.fragments.home.map;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ubermobileapp.R;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import com.example.ubermobileapp.activities.inbox.ChatActivity;
import com.example.ubermobileapp.models.pojo.communication.Rejection;
import com.example.ubermobileapp.models.pojo.ride.Panic;
import com.example.ubermobileapp.models.pojo.ride.Ride;
import com.example.ubermobileapp.models.pojo.user.Passenger;
import com.example.ubermobileapp.models.pojo.user.User;
import com.example.ubermobileapp.services.implementation.PassengerService;
import com.example.ubermobileapp.services.implementation.RideService;
import com.example.ubermobileapp.services.utils.AuthService;
import com.example.ubermobileapp.tools.Timer;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.EncodedPolyline;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MapFragment extends Fragment implements LocationListener, OnMapReadyCallback {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private LocationManager locationManager;
    private String provider;
    private SupportMapFragment mMapFragment;
    private AlertDialog dialog;
    private Marker home;
    private GoogleMap map;
    private Marker departure;
    private Marker destination;
    private List<Polyline> polylines = new ArrayList<>();
    private Timer timer = Timer.getInstance();
    private boolean play = false;
    private AlertDialog alertDialog;
    private Ride ride;
    private String reason;

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        User user = AuthService.getCurrentUser();
        if (user.getRoles().get(0).getName().equals("ROLE_PASSENGER")) {
            try {
                ride = RideService.getPassengerActiveRide(user.getId());
                setData();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            ride = RideService.getDriverActiveRide(user.getId());

            if (ride != null){
                setData();
            }
            else {
                ride = RideService.getDriverAcceptedRide(user.getId());
            }
        }

    }

    private void createMapFragmentAndInflate() {
        Criteria criteria = new Criteria();

        provider = locationManager.getBestProvider(criteria, true);

        mMapFragment = SupportMapFragment.newInstance();

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.map_container, mMapFragment).commit();

        mMapFragment.getMapAsync(this);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onResume() {
        super.onResume();

        createMapFragmentAndInflate();

        if (checkLocationPermission()) {
            if (ContextCompat.checkSelfPermission(requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ) {

                //Request location updates:
                locationManager.requestLocationUpdates(provider, 2000, 0, this);
            }else if(ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){

                //Request location updates:
                locationManager.requestLocationUpdates(provider, 2000, 0, this);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle data) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_map, vg, false);
        return view;
    }

    @Override
    public void onLocationChanged(Location location) {
        if (map != null) {
            addMarker(location);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(getActivity())
                        .setTitle("Allow user location")
                        .setMessage("To continue working we need your location....Allow now?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{
                                                Manifest.permission.ACCESS_FINE_LOCATION,
                                                Manifest.permission.ACCESS_COARSE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        Location location = null;

        if (provider == null) {
            Log.i("ASD", "Onmapre");

        }else {
            if (checkLocationPermission()) {
                Log.i("ASD", "str" + provider);


                if (ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    //Request location updates:
                    location = locationManager.getLastKnownLocation(provider);
                } else if (ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    //Request location updates:
                    location = locationManager.getLastKnownLocation(provider);
                }
            }
        }

        try {
            if (ride != null) writeOnClickListeners();
            else Toast.makeText(getActivity(), "No scheduled rides.", Toast.LENGTH_SHORT).show();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                // TODO : add here what you want to happen
                return false;
            }
        });
        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
            }
        });

        if (location != null) {
            addMarker(location);
            if (play) drawRoute();
        }
    }

    private void addMarker(Location location) {
        LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());

        if (home != null) {
            home.remove();
        }

        home = map.addMarker(new MarkerOptions()
                .title("Your location")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                .position(loc));
        home.setFlat(true);

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(loc).zoom(14).build();

        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void onPause() {
        super.onPause();

        locationManager.removeUpdates(this);
    }

    public void drawRoute() {
        com.example.ubermobileapp.models.pojo.ride.Location locationDeparture =
                ride.getLocations().get(0).getDeparture();
        com.example.ubermobileapp.models.pojo.ride.Location locationDestination =
                ride.getLocations().get(0).getDestination();
        LatLng first_marker = new LatLng(locationDeparture.getLatitude(),
                                         locationDeparture.getLongitude());
        departure = map.addMarker(new MarkerOptions().position(first_marker).title("Departure"));

        LatLng second_marker = new LatLng(locationDestination.getLatitude(),
                                          locationDestination.getLongitude());
        destination = map.addMarker(new MarkerOptions().position(second_marker).title("Destination"));

        LatLng zaragoza = first_marker;

        //Define list to get all latlng for the route
        List<LatLng> path = new ArrayList();


        //Execute Directions API request
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyCfc8_yjmToWtKOMhh5NExf-ht_TgR_Fys")
                .build();
        DirectionsApiRequest req = DirectionsApi.getDirections(context, first_marker.latitude
                        + "," + first_marker.longitude,
                second_marker.latitude + "," + second_marker.longitude);

        try {
            DirectionsResult res = req.await();
            //Loop through legs and steps to get encoded polylines of each step
            if (res.routes != null && res.routes.length > 0) {
                DirectionsRoute route = res.routes[0];

                if (route.legs !=null) {
                    for(int i=0; i<route.legs.length; i++) {
                        DirectionsLeg leg = route.legs[i];
                        if (leg.steps != null) {
                            for (int j=0; j<leg.steps.length;j++){
                                DirectionsStep step = leg.steps[j];
                                if (step.steps != null && step.steps.length >0) {
                                    for (int k=0; k<step.steps.length;k++){
                                        DirectionsStep step1 = step.steps[k];
                                        EncodedPolyline points1 = step1.polyline;
                                        if (points1 != null) {
                                            //Decode polyline and add points to list of route coordinates
                                            List<com.google.maps.model.LatLng> coords1 = points1.decodePath();
                                            for (com.google.maps.model.LatLng coord1 : coords1) {
                                                path.add(new LatLng(coord1.lat, coord1.lng));
                                            }
                                        }
                                    }
                                } else {
                                    EncodedPolyline points = step.polyline;
                                    if (points != null) {
                                        //Decode polyline and add points to list of route coordinates
                                        List<com.google.maps.model.LatLng> coords = points.decodePath();
                                        for (com.google.maps.model.LatLng coord : coords) {
                                            path.add(new LatLng(coord.lat, coord.lng));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch(Exception ex) {
            Log.e("something", ex.getLocalizedMessage());
        }
        //Draw the polyline
        if (path.size() > 0) {
            PolylineOptions opts = new PolylineOptions().addAll(path).color(Color.GREEN).width(5);
            polylines.add(map.addPolyline(opts));
        }

        map.getUiSettings().setZoomControlsEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(zaragoza, 15));
    }

    private void writeOnClickListeners() throws ParseException {
        CardView passengers = getActivity().findViewById(R.id.firstCard);
        passengers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    createRideInfoDialog();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        CardView start_pause = getActivity().findViewById(R.id.thirdCard);
        if (start_pause != null) {
            start_pause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!play) {
                        ride = RideService.getDriverAcceptedRide(AuthService.getCurrentUser().getId());
                        if (ride != null) {
                            ride = RideService.start(requireActivity().getApplicationContext(),
                                    ride.getId(), "Current ride not found");
                            drawRoute();
                            setUpTimer();
                        } else {
                            Toast.makeText(getActivity(), "No accepted rides.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        play = false;
                        removeRoute();
                        ride = RideService.end(ride.getId());
                        ride = null;
                        timer.onClickReset();
                    }
                }
            });
        }

        CardView panic = getActivity().findViewById(R.id.secondCard);
        panic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                play = false;
                removeRoute();
                com.example.ubermobileapp.models.pojo.communication.Message message =
                        new com.example.ubermobileapp.models.pojo.communication.Message();
                message.setMessage(reason);
                createPanicDialog();
                timer.onClickReset();
            }
        });

    }

    private void removeRoute() {
        destination.remove();
        departure.remove();
        for (Polyline polyline : polylines) {
            polyline.remove();
        }
    }

    public void createRideInfoDialog() throws InterruptedException {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = this.getLayoutInflater();
        View newView = inflater.inflate(R.layout.fragment_ride_info, null);
        String number = getRide(newView);
        if (Objects.equals(number, "")) return;
        ImageView call = newView.findViewById(R.id.call);
        call.setClickable(true);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialNumber(number);
            }
        });
        dialog.setView(newView)
                .setTitle("Ride Info")
                .setCancelable(true)
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        alertDialog = dialog.create();
        alertDialog.show();

        ImageView chat = newView.findViewById(R.id.chat);
        chat.setClickable(true);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                //intent.putExtra("name", name);
                startActivity(intent);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public String getRide(View view) throws InterruptedException {
        User user = AuthService.getCurrentUser();
        Ride ride;
        String number;
        if (user.getRoles().get(0).getName().equals("ROLE_PASSENGER")) {
            ride = RideService.getPassengerActiveRide(user.getId());
            TextView email = view.findViewById(R.id.emailInfo);
            email.setText(ride.getDriver().getEmail());
            TextView name = view.findViewById(R.id.nameInfo);
            name.setText(ride.getDriver().getName() + " " + ride.getDriver().getSurname());
            TextView phone = view.findViewById(R.id.phoneNumber);
            phone.setText(ride.getDriver().getTelephoneNumber());
            number = ride.getDriver().getTelephoneNumber();
        } else {
            ride = RideService.getDriverActiveRide(user.getId());
            if (ride == null) {
                Toast.makeText(getActivity(), "No active rides.", Toast.LENGTH_SHORT).show();
                return "";
            }
//            Optional<User> first = ride.getPassengers().stream().findFirst();
            Optional<User> last = Optional.of(ride.getPassengers().stream().reduce((one, two) -> two).get());
            Passenger passenger = PassengerService.getPassenger(last.get().getId());
            TextView email = view.findViewById(R.id.emailInfo);
            email.setText(passenger.getEmail());
            TextView name = view.findViewById(R.id.nameInfo);
            name.setText(passenger.getName() + " " + passenger.getSurname());
            TextView phone = view.findViewById(R.id.phoneNumber);
            phone.setText(passenger.getTelephoneNumber());
            number = passenger.getTelephoneNumber();
        }
        TextView startDate = view.findViewById(R.id.startDate);
        startDate.setText(ride.getScheduledTime());
        TextView estimatedTime = view.findViewById(R.id.estimatedTime);
        estimatedTime.setText(Double.toString(ride.getEstimatedTimeInMinutes()));
        TextView price = view.findViewById(R.id.price);
        price.setText(Double.toString(ride.getTotalCost()));
        TextView babyTransport = view.findViewById(R.id.babyTransport);
        babyTransport.setText(Boolean.toString(ride.isBabyTransport()));
        TextView petTransport = view.findViewById(R.id.petTransport);
        petTransport.setText(Boolean.toString(ride.isPetTransport()));

        return number;
    }

    private void openDialNumber(String number) {
       Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel: " + Uri.encode(number)));
       startActivity(intent);
    }

    @SuppressLint("SimpleDateFormat")
    private void setData() {
        if (ride != null) {
            setUpTimer();
        }
    }

    private void setUpTimer() {
        @SuppressLint("SimpleDateFormat")
        Date startTime = null;
        try {
            startTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .parse(ride.getStartTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert startTime != null;
        long seconds = (new Date().getTime()-startTime.getTime())/1000;
        timer.setSeconds((int)seconds);
        timer.setRunning(true);
        play = true;
    }

    void createPanicDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View newView = inflater.inflate(R.layout.fragment_inbox_search, null);
        builder.setTitle("Tell us what happened")
                .setView(newView)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditText s = newView.findViewById(R.id.search);
                        reason = s.getText().toString();
                        Panic panic = new Panic(reason);
                        ride = RideService.panic(ride.getId(), panic);
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.create();
        builder.show();
    }
}