package com.example.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.core.entities.AtributiKardioVjezbi;
import com.example.database.MyDatabase;


public class AtributiKardioVjezbiDAL {

    // CRUD

    public static void Create(Context context, AtributiKardioVjezbi... atributiKardioVjezbis){
        new CreateAsyncTask(context).execute(atributiKardioVjezbis);
    }

    public static void Update(Context context, AtributiKardioVjezbi... atributiKardioVjezbis){
        new UpdateAsyncTask(context).execute(atributiKardioVjezbis);
    }

    public static void Delete(Context context, AtributiKardioVjezbi... atributiKardioVjezbis){
        new DeleteAsyncTask(context).execute(atributiKardioVjezbis);
    }


    //LIVE

    public static LiveData<AtributiKardioVjezbi> ReadById(String id, Context context){
        return MyDatabase.getInstance(context).getAtributiKardioVjezbioDAO().readById(id);
    }

    public static LiveData<AtributiKardioVjezbi> ReadByKorisnikVjezbaId(String korisnikVjezbaId, Context context){
        return MyDatabase.getInstance(context).getAtributiKardioVjezbioDAO().readByKorisnikVjezba(korisnikVjezbaId);
    }

    public static LiveData<AtributiKardioVjezbi> CreateEmpty(Context context, int korisnikVjezbaId) {
        long id = MyDatabase.getInstance(context).getAtributiKardioVjezbioDAO().createEmpty(korisnikVjezbaId);
        return MyDatabase.getInstance(context).getAtributiKardioVjezbioDAO().readById(String.valueOf(id));
    }

    //ASYNC
    private static class CreateAsyncTask extends AsyncTask<AtributiKardioVjezbi, Void, Void> {
        private Context context;

        private CreateAsyncTask(Context context){
            this.context = context;
        }

        @Override
        protected Void doInBackground(AtributiKardioVjezbi... atributiKardioVjezbis) {
            MyDatabase.getInstance(context).getAtributiKardioVjezbioDAO().create(atributiKardioVjezbis);
            return null;
        }
    }


    private static class UpdateAsyncTask extends AsyncTask<AtributiKardioVjezbi, Void, Void>{
        private  Context context;

        private UpdateAsyncTask(Context context) {this.context = context;}

        @Override
        protected Void doInBackground(AtributiKardioVjezbi... atributiKardioVjezbis){
            MyDatabase.getInstance(context).getAtributiKardioVjezbioDAO().update(atributiKardioVjezbis);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<AtributiKardioVjezbi, Void, Void>{
        private Context context;

        private DeleteAsyncTask(Context context){
            this.context = context;
        }

        @Override
        protected Void doInBackground(AtributiKardioVjezbi... atributiKardioVjezbis) {
            MyDatabase.getInstance(context).getAtributiKardioVjezbioDAO().delete(atributiKardioVjezbis);
            return null;
        }
    }


}
