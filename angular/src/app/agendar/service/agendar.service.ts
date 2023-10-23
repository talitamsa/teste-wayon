import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Agendar } from '../model/agendar';
import { delay, first, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AgendarService {

  private readonly API = '/assets/transferencia.json';

  constructor(private httpClient: HttpClient) { }

  list() {
    return this.httpClient.get<Agendar[]>(this.API).
    pipe(
      first(),
      delay(3000),
      tap(agendar => console.log(agendar))
    );
  }
}
