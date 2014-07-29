/***********************************************************************************
 *  Copyright 2014 IBM
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
**********************************************************************************/
package com.bluemix.hangman.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bluemix.hangman.data.CloudantConnection;
import com.bluemix.hangman.model.Word;

/**
 * Servlet implementation class LoadGame
 */
@WebServlet("/LoadGame")
public class LoadGame extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoadGame() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  String action = request.getParameter("action");
		  String value = request.getParameter("value");
		  
		  if ((action != null)&&(value != null)) {
				CloudantConnection cloudantConnection = new CloudantConnection();
				Word word = cloudantConnection.getRandomWordByCategory(value);
				if(word!=null){
					response.setContentType("text/html");
					response.getWriter().write(word.getName());
				}
		  }
	}

}