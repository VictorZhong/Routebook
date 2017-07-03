package me.routebook.routebook;

import android.os.Bundle;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.utils.overlay.SmoothMoveMarker;

import java.util.ArrayList;
import java.util.List;

import me.routebook.routebook.base.SimpleActivity;

public class MainActivity extends SimpleActivity {

    private MapView mMapView = null;
    private AMap aMap;

    List<LatLng> points = new ArrayList<>();
    LatLngBounds bounds = new LatLngBounds(points.get(0), points.get(points.size() - 2));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMapView = (MapView) findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        aMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));
        SmoothMoveMarker smoothMoveMarker = new SmoothMoveMarker(aMap);
//        smoothMoveMarker.setDescriptor(BitmapDescriptorFactory.fromResource(R.drawable.icon_car));

    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

}
