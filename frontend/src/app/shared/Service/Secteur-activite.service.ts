import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, tap } from 'rxjs/operators';
import { Observable, throwError } from 'rxjs';
import { NGXLogger } from 'ngx-logger';
import { LoggerService } from './LoggerService'; // Adjust path if needed

// Optional: Define SecteurActivite interface for type safety
interface SecteurActivite {
  idSecteurActivite?: number | null;
  codeSecteurActivite?: string | null;
  libelleSecteurActivite?: string | null;
}

@Injectable({
  providedIn: 'root'
})
export class SecteurActiviteService {
  readonly API_URL = 'http://localhost:8089/SpringMVC/secteurActivite';

  constructor(
    private httpClient: HttpClient,
    private logger: NGXLogger,
    private loggerService: LoggerService
  ) { }

  getAllSecteurActivites(): Observable<SecteurActivite[]> {
    this.loggerService.log('Appel API pour récupérer tous les secteurs activites');
    this.logger.debug('Appel API pour récupérer tous les secteurs activites');
    return this.httpClient.get<SecteurActivite[]>(`${this.API_URL}/retrieve-all-secteurActivite`).pipe(
      tap(response => {
        this.loggerService.log('Réponse obtenue avec succès', 'INFO');
        this.logger.info('Secteurs activites récupérés avec succès', response);
      }),
      catchError(error => {
        this.loggerService.log(`Erreur API: ${error.message}`, 'ERROR');
        this.logger.error('Erreur lors de la récupération des secteurs activites', error);
        return throwError(() => new Error('Erreur API'));
      })
    );
  }

  addSecteurActivite(secteurActivite: SecteurActivite): Observable<SecteurActivite> {
    this.loggerService.log(`Appel API pour ajouter un secteur activite: ${JSON.stringify(secteurActivite)}`);
    this.logger.debug('Appel API pour ajouter un secteur activite', secteurActivite);
    return this.httpClient.post<SecteurActivite>(`${this.API_URL}/add-secteurActivite`, secteurActivite).pipe(
      tap(response => {
        this.loggerService.log('Secteur activite ajouté avec succès', 'INFO');
        this.logger.info('Secteur activite ajouté avec succès', response);
      }),
      catchError(error => {
        this.loggerService.log(`Erreur API: ${error.message}`, 'ERROR');
        this.logger.error('Erreur lors de l\'ajout du secteur activite', error);
        return throwError(() => new Error('Erreur lors de l\'ajout du secteur activite'));
      })
    );
  }

  editSecteurActivite(secteurActivite: SecteurActivite): Observable<SecteurActivite> {
    this.loggerService.log(`Appel API pour modifier le secteur activite: ${JSON.stringify(secteurActivite)}`);
    this.logger.debug('Appel API pour modifier le secteur activite', secteurActivite);
    return this.httpClient.put<SecteurActivite>(`${this.API_URL}/modify-secteurActivite`, secteurActivite).pipe(
      tap(response => {
        this.loggerService.log('Secteur activite modifié avec succès', 'INFO');
        this.logger.info('Secteur activite modifié avec succès', response);
      }),
      catchError(error => {
        this.loggerService.log(`Erreur API: ${error.message}`, 'ERROR');
        this.logger.error('Erreur lors de la modification du secteur activite', error);
        return throwError(() => new Error('Erreur lors de la modification du secteur activite'));
      })
    );
  }

  deleteSecteurActivite(idSecteurActivite: number): Observable<void> {
    this.loggerService.log(`Appel API pour supprimer le secteur activite: ${idSecteurActivite}`);
    this.logger.debug('Appel API pour supprimer le secteur activite', idSecteurActivite);
    return this.httpClient.delete<void>(`${this.API_URL}/remove-secteurActivite/${idSecteurActivite}`).pipe(
      tap(() => {
        this.loggerService.log('Secteur activite supprimé avec succès', 'INFO');
        this.logger.info(`Secteur activite supprimé avec succès (ID: ${idSecteurActivite})`);
      }),
      catchError(error => {
        this.loggerService.log(`Erreur API: ${error.message}`, 'ERROR');
        this.logger.error('Erreur lors de la suppression du secteur activite', error);
        return throwError(() => new Error('Erreur lors de la suppression du secteur activite'));
      })
    );
  }
}

// 🔽 TEST UNITAIRE 🔽
import { TestBed } from '@angular/core/testing';

describe('SecteurActiviteService', () => {
  let service: any;
  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SecteurActiviteService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
