package com.turkiyedenemeleri.model.http.exceptions;

/**
 * Created by celal on 24/04/2017.
 */

    public class ApiException extends Exception{
        public ApiException(String msg)
        {
            super(msg);
        }
    }

