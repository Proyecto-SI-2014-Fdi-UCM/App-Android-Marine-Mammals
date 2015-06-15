package com.example.drugsformarinemammals;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

public class Fragment_About extends Fragment {

	private View rootView; 
	private ExpandableListView expandableAbout;
	private HashMap<Integer, List<ItemWithImage>> childAbout;
	private List<Integer> headers;
	private ItemExpandableListViewAdapter adapterAbout;

		
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_about,container, false);
		initializeListViewWithImage();
		expandableAbout = (ExpandableListView) rootView.findViewById(R.id.expandableAbout);
		adapterAbout = new ItemExpandableListViewAdapter(getActivity(),headers, childAbout, expandableAbout);
		expandableAbout.setAdapter(adapterAbout);
		expandableAbout.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView arg0, View arg1, int positionGroup, int positionChild, long arg4) {
				if(positionGroup==1 && positionChild ==6){
					String[] urls={"http://formmulary.tk/Android/getDrugNames.php"};
					new GetDrugNamesAndCreatePdf(rootView.getContext()).execute(urls);
				}
				else 
					openPdf(positionGroup,positionChild );
				
				return true;
			}
		});

		return rootView;
	}
	
	public void openPdf(int positionGroup, int positionChild ){
		String fileName="";
		if(positionGroup==0){
			switch(positionChild){
				case 0: fileName="generaloverview.pdf";
						break;
				//case 1: break;
				case 1: fileName="challengesinmarinemammalsmedication.pdf";
						break;
			}
		}
		else{
			switch(positionChild){
				case 0: fileName="userGuide.pdf";
						break;
				case 1: fileName="disclaimer.pdf";
						break;
				case 2: fileName="authors.pdf";
						break;
				case 3: fileName="support.pdf";
						break;
				case 5: fileName="bibliography.pdf";
						break;
				//case 6: fileName="druglist.pdf";
				
				
			}
		}
		
		AssetManager assetManager = rootView.getContext().getAssets();

        InputStream in = null;
        OutputStream out = null;
        File file = new File(rootView.getContext().getFilesDir(), fileName);
        try
        {
            in = assetManager.open(fileName);
            out = rootView.getContext().openFileOutput(file.getName(), Context.MODE_WORLD_READABLE);

            copyFile(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (Exception e)
        {
            Log.e("tag", e.getMessage());
        }

		
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.parse("file://" + rootView.getContext().getFilesDir()+ "/"+fileName), "application/pdf");
		startActivity(intent);
	}
		
	
	private void copyFile(InputStream in, OutputStream out) throws IOException
    {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1)
        {
            out.write(buffer, 0, read);
        }
    }

	private void initializeListViewWithImage() {

		childAbout = new HashMap<Integer, List<ItemWithImage>>();
		List<ItemWithImage> itemsAboutMarineMammals = new ArrayList<ItemWithImage>();
		headers = new ArrayList<Integer>();
		itemsAboutMarineMammals.add(new ItemWithImage(R.drawable.ageneraloverview, "Marine Mammals: a general overview"));
		itemsAboutMarineMammals.add(new ItemWithImage(R.drawable.routes, "Challenges In Marine Mammals Medication"));
		childAbout.put(1, itemsAboutMarineMammals);
		headers.add(1);
		
		List<ItemWithImage> itemsAboutFormulary = new ArrayList<ItemWithImage>();
		itemsAboutFormulary.add((new ItemWithImage(R.drawable.user_guide, "User's guide")));
		itemsAboutFormulary.add((new ItemWithImage(R.drawable.disclaimer, "Disclaimer")));
		itemsAboutFormulary.add((new ItemWithImage(R.drawable.authors, "Authorship")));
		itemsAboutFormulary.add((new ItemWithImage(R.drawable.support, "Support")));
		itemsAboutFormulary.add((new ItemWithImage(R.drawable.rate, "Rate The App")));
		itemsAboutFormulary.add((new ItemWithImage(R.drawable.bibliography, "Bibliography")));
		itemsAboutFormulary.add((new ItemWithImage(R.drawable.druglist, "Drug list")));
		childAbout.put(2, itemsAboutFormulary);
		headers.add(2);

	}		
	
	public class GetDrugNamesAndCreatePdf extends AsyncTask<String, Void, String>{
		String jsonResponse;
		Context context;
		ArrayList<String> drugNames=new ArrayList<String>();
		public GetDrugNamesAndCreatePdf(Context c){
			context=c;
		}
		@Override
		protected String doInBackground(String... urls) {
			HttpPost httppost;
			HttpClient httpclient = new DefaultHttpClient();
			httppost = new HttpPost(urls[0]);
			
			try{
				HttpResponse response = httpclient.execute(httppost);
		        jsonResponse = EntityUtils.toString(response.getEntity());		        
		
		  }catch (Exception e) {
		        Log.v("Error: ", e.getMessage());
		  }
		  return jsonResponse;
		}
		
		protected void onPostExecute(String result) {
			Calendar c=Calendar.getInstance();
			String day=Integer.toString(c.get(Calendar.DAY_OF_MONTH));
			String month = Integer.toString(c.get(Calendar.MONTH)+1);
			String year = Integer.toString(c.get(Calendar.YEAR));
			initializeDrugNames(result);
			String filename = "drugList.pdf";
			try {
				//Creamos el fichero en la memoria interna del teléfono
				FileOutputStream outputStream;
				outputStream = context.openFileOutput(filename, Context.MODE_WORLD_READABLE);
				//Librería itextg para crear un doumento pdf
				//Creamos un fichero tipo Document
				Document document = new Document();
				//Asociamos el Document a nuestro fichero pdf
	            PdfWriter.getInstance(document,outputStream);
	            //Abrimos el Document
	            document.open();
	            //Escribimos el contenido del fichero
	            document.add(new Paragraph("DRUG LIST IN THE FORMMULARY® (by alphabetical order) updated on "+day+"/"+month+"/"+year));
	            int size=drugNames.size();
		        for(int i=0;i<size;i++){
		        	document.add(new Paragraph(drugNames.get(i)));
		        }
				document.close();
				outputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.parse("file://"+context.getFilesDir()+ "/"+"drugList.pdf"), "application/pdf");
			startActivity(intent);
		}
		public void initializeDrugNames(String result){			
			String []parse= result.split("\\[\"");
			int size=parse.length;
			for(int i=1;i<size;i++){
				String[] tmp=parse[i].split("\"\\]");
				drugNames.add(tmp[0]);
			}
		}
	}

	
}