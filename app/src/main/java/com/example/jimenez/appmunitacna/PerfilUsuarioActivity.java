package com.example.jimenez.appmunitacna;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.jimenez.appmunitacna.Clases.Usuario;
import com.example.jimenez.appmunitacna.objects.FirebaseReferences;
import com.example.jimenez.appmunitacna.objects.Global;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PerfilUsuarioActivity extends AppCompatActivity {


    private static final String TAG = "FirebaseDataSnapshot";
    @BindView(R.id.tvProfileName)
    TextView tvProfileName;
    @BindView(R.id.tvProfileCorreo)
    TextView tvProfileCorreo;
    @BindView(R.id.ivProfileUser)
    ImageView ivProfileUser;
    @BindView(R.id.etPhoneNumber)
    EditText etPhoneNumber;
    @BindView(R.id.etDni)
    EditText etDni;
    @BindView(R.id.etAddress)
    EditText etAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);
        ButterKnife.bind(this);
        initializeData();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    private void initializeData() {


        tvProfileName.setText(Global.getCurrentDataUser().getNombres());
        tvProfileCorreo.setText(Global.getCurrentDataUser().getCorreo());
        etPhoneNumber.setText(Global.getCurrentDataUser().getCelular());
        etDni.setText(Global.getCurrentDataUser().getDni());
        etAddress.setText(Global.getCurrentDataUser().getDireccion());

        Log.d(TAG, "" + Global.getGlobalDataUser().getDisplayName());
        if (Global.getGlobalDataUser().getPhotoUrl() != null) {
            Glide.with(this)
                    .load(Global.getGlobalDataUser().getPhotoUrl())
                    .into(ivProfileUser);
        } else {
            ivProfileUser.setImageResource(R.drawable.ic_star);
        }

    }

    @OnClick(R.id.btnActualizarDatos)
    public void onUpdateData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference reference = database.getReference(FirebaseReferences.USERS_REFERENCE);


        String fullname = tvProfileName.getText().toString();
        String email = tvProfileCorreo.getText().toString();
        final String phoneNumber=etPhoneNumber.getText().toString();
        String dni=etDni.getText().toString();
        String address=etAddress.getText().toString();

        final Usuario currentUser = new Usuario(fullname, email, phoneNumber, dni,address);

        Query query=reference.orderByChild("correo").equalTo(Global.getGlobalDataUser().getEmail()); // de la bd selecciono nodo que contiene al usuario actual
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                    String currentUserKey = childSnapshot.getKey();
                    Log.d("FirebaseID", "" + currentUserKey);
                    dataSnapshot.getRef().child(currentUserKey).setValue(currentUser);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("ErrorFirebase", "getUser:onCancelled", databaseError.toException());
            }
        });
        Toast.makeText(this, "Datos Actualizados", Toast.LENGTH_SHORT).show();
        cargarDatosUsuario();

    }

    private void cargarDatosUsuario() {
        final FirebaseAuth mAuth=FirebaseAuth.getInstance();
        FirebaseUser user=mAuth.getCurrentUser();

        Global.setGlobalDataUser(user);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference reference = database.getReference(FirebaseReferences.USERS_REFERENCE);

        Query query=reference.orderByChild("correo").equalTo(Global.getGlobalDataUser().getEmail()); // de la bd selecciono nodo que contiene al usuario actual
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                    String currentUserKey = childSnapshot.getKey();
                    Log.d(TAG, "" + dataSnapshot.getValue());
                    Global.setCurrentDataUser(dataSnapshot.child(currentUserKey).getValue(Usuario.class));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //Usuario currentUserData= GlobalCurrentUser.getCompleteUserData();
    }
}
