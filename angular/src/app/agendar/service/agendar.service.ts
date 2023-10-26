import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';

import { Agendar } from '../model/agendar';
import { catchError, delay, first, of, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AgendarService {

  private readonly API = 'transferencias';

  constructor(private httpClient: HttpClient) { }

  list() {
    return this.httpClient.get<Agendar[]>(this.API).
    pipe(
      first(),
      delay(1000),
      tap(agendar => console.log(agendar))
    );
  }

  save(registro: Partial<Agendar>) {
    return this.httpClient.post<Agendar>(this.API, registro).pipe(
      catchError((error) => {
        return of({ success: false, message: 'Erro ao salvar a transferência.', error: error });
      })
    );
  }

  remove(id: number) {
    return this.httpClient.delete(`${this.API}/${id}`).pipe(first());
  }

}
