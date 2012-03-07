package tracking.life.android;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MapView.LayoutParams;

public class GoogleMapActivity extends MapActivity  {

	MapView mapView; 
    MapController mc;
    GeoPoint p;
	
	public boolean onKeyDown(int keyCode, KeyEvent event) 
    {
        MapController mc = mapView.getController(); 
        switch (keyCode) 
        {
            case KeyEvent.KEYCODE_3:
                mc.zoomIn();
                break;
            case KeyEvent.KEYCODE_1:
                mc.zoomOut();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }   
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        //Pulling xml data from xml file
        setContentView(R.layout.main);
        
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.setSatellite(true);
        //mapView.setStreetView(true);
        LinearLayout zoomLayout = (LinearLayout)findViewById(R.id.zoom);  
        View zoomView = mapView.getZoomControls(); 
 
//        zoomLayout.addView(zoomView, 
//            new LinearLayout.LayoutParams(
//                LayoutParams.WRAP_CONTENT, 
//                LayoutParams.WRAP_CONTENT)); 
//        mapView.displayZoomControls(true);
        
        zoomLayout.addView(zoomView, 
                new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT, 
                    LayoutParams.WRAP_CONTENT)); 
            mapView.displayZoomControls(true);
     
            mc = mapView.getController();
            String coordinates[] = {"1.352566007", "103.78921587"};
            double lat = Double.parseDouble(coordinates[0]);
            double lng = Double.parseDouble(coordinates[1]);
     
            p = new GeoPoint(
                (int) (lat * 1E6), 
                (int) (lng * 1E6));
     
            mc.animateTo(p);
            mc.setZoom(17); 
            mapView.invalidate();
    }
 
    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }

}
