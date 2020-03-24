package com.example.mycontacts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CreateContact extends  AppCompatActivity implements  View.OnClickListener {

    ArrayList<Contact> contactsList;
    public EditText Name;
    public EditText Telefono;
    public Button save;
    ListView arrayView;
    String escogido;
    String empty="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        Name = (EditText) findViewById(R.id.editNombre);
        Telefono = (EditText) findViewById(R.id.editTelefono);
        arrayView = (ListView) findViewById(R.id.ListView2);
        escogido = (String) arrayView.getItemAtPosition(2);

        registerForContextMenu(Telefono);
        registerForContextMenu(Name);

        ArrayAdapter<CharSequence> adaptador = ArrayAdapter.createFromResource(this, R.array.select, android.R.layout.simple_list_item_single_choice);
        arrayView.setAdapter(adaptador);

        save = (Button) findViewById(R.id.btnGuardar);
        save.setOnClickListener(this);


        Intent intent = getIntent();
        ArrayList<Contact> arrayParents = intent.getParcelableArrayListExtra("Contact");

        if(arrayParents != null && arrayParents.size() > 0) {
            contactsList = arrayParents;
        }else {
            contactsList = new ArrayList<Contact>();
        }


        arrayView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String seleccion = (String) arrayView.getItemAtPosition(position);
                escogido = seleccion;
                view.setSelected(true);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGuardar:
                save();
                break;
        }
    }


    private void save() {
        if(!Name.getText().toString().trim().isEmpty() && !Telefono.getText().toString().trim().isEmpty() && !escogido.isEmpty()){
            Contact newPerson;

            String name = Name.getText().toString().trim();
            String telefono = Telefono.getText().toString().trim();

            newPerson = new Contact(telefono, name, escogido);
            contactsList.add(newPerson);

            Intent intent = new Intent(this,MainActivity.class);
            intent.putParcelableArrayListExtra("Contact",contactsList);
            startActivity(intent);

            Log.e("Informacion","Sirve" + contactsList);

            Toast.makeText(getApplicationContext(), "Success, Added contact", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"All Field are required", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contact,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.menu_second:
                Name.setText(empty);
                Telefono.setText(empty);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public  void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo MenuInfo) {
        super.onCreateContextMenu(menu, v, MenuInfo);

        if(v == Name) {
            getMenuInflater().inflate(R.menu.menu_uppercase_name, menu);
        }else if(v == Telefono){
            getMenuInflater().inflate(R.menu.menu_contextual_ramdon_number, menu);
        }
    }

    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case    R.id.ctx_RNumber:
                ramdonnumber();
                return true;
            case R.id.ctx_UpperCaseName:
                uppercaseName();
                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }

    //Generate aleatory number
    private  void ramdonnumber() {
        List<Integer> list = Arrays.asList(300, 310, 320);
        Random extra = new Random();
        int number1 = list.get(extra.nextInt(list.size()));
        int number2 = (int) (10000000 * Math.random());
        String numero = String.valueOf(number1) + String.valueOf(number2);
        Telefono.setText(numero);
    }

    private void uppercaseName(){
        String name = Name.getText().toString().toUpperCase();
        Name.setText(name);
    }
}




