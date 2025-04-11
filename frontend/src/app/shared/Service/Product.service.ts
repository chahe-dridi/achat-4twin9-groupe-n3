import { Injectable } from '@angular/core';

import { HttpClient} from '@angular/common/http';
import { catchError, tap } from 'rxjs/operators';  // Import des opérateurs RxJS
import { throwError } from 'rxjs';  // Pour lancer des erreurs
import { NGXLogger } from 'ngx-logger';  // Assurez-vous que NGXLogger est correctement importé
import { Observable } from 'rxjs';
import { LoggerService } from './LoggerService';


@Injectable({
  providedIn: 'root'
})
export class ProductService {
  readonly API_URL = 'http://localhost:8089/SpringMVC/produit';

  constructor(private httpClient: HttpClient, private logger: NGXLogger, private logService: LoggerService) { }

  // Récupérer tous les produits
  getAllProducts(): Observable<any> { 
    this.logService.log('Appel API pour récupérer tous les produits');

    return this.httpClient.get<any[]>(`${this.API_URL}/retrieve-all-produits`).pipe(
      tap(response => {
        this.logService.log('Réponse obtenue avec succès', 'INFO');
      }),
      catchError(error => {
        this.logService.log(`Erreur API: ${error.message}`, 'ERROR');
        return throwError(() => new Error('Erreur API'));
      })
    );
}


  // Ajouter un produit
  addProduct(product: any): Observable<any> {
    this.logger.debug('Appel API pour ajouter un produit', product);
    return this.httpClient.post<any>(`${this.API_URL}/add-produit`, product)
      .pipe(
        tap(response => {
          console.log('Réponse reçue :', response);  // Vérification
          this.logger.info('Produit ajouté avec succès', response);
        }),
        catchError(error => {
          this.logger.error('Erreur lors de l\'ajout du produit', error);
          return throwError(() => new Error('Erreur lors de l\'ajout du produit'));
        })
      );
}

  // Modifier un produit
  editProduct(product: any): Observable<any> {
    this.logger.debug('Appel API pour modifier le produit', product);
    return this.httpClient.put<any>(`${this.API_URL}/modify-produit`, product)
      .pipe(
        tap(response => this.logger.info('Produit modifié avec succès', response)),
        catchError(error => {
          this.logger.error('Erreur lors de la modification du produit', error);
          return throwError(() => new Error('Erreur lors de la modification du produit'));
        })
      );
  }

  // Supprimer un produit
  deleteProduct(idProduct: number): Observable<any> {
    this.logger.debug('Appel API pour supprimer le produit', idProduct);
    return this.httpClient.delete(`${this.API_URL}/remove-produit/${idProduct}`)
      .pipe(
        tap(() => this.logger.info(`Produit supprimé avec succès (ID: ${idProduct})`)),
        catchError(error => {
          this.logger.error('Erreur lors de la suppression du produit', error);
          return throwError(() => new Error('Erreur lors de la suppression du produit'));
        })
      );
  }
}