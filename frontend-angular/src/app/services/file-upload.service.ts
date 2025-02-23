import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FileUploadService {
  private apiUrl = 'http://localhost:8088/accounts/create'; // URL del backend

  constructor(private http: HttpClient) {}

  uploadClients(clientes: any[]): Observable<any> {
    return this.http.post(this.apiUrl, clientes);
  }
}
