import { ComponentFixture, TestBed } from '@angular/core/testing';
import { OperateurComponent } from './operateur.component';
import { OperateurService } from '../shared/Service/Operateur.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { Operateur } from '../shared/Model/Operateur';
import { of, throwError } from 'rxjs';

describe('OperateurComponent', () => {
  let component: OperateurComponent;
  let fixture: ComponentFixture<OperateurComponent>;
  let mockOperateurService: jasmine.SpyObj<OperateurService>;
  let mockModalService: jasmine.SpyObj<NgbModal>;

  beforeEach(async () => {
    mockOperateurService = jasmine.createSpyObj('OperateurService', [
      'getAllOperateurs',
      'addOperateur',
      'editOperateur',
      'deleteOperateur'
    ]);

    // Initialize with empty array to avoid undefined errors in ngOnInit
    mockOperateurService.getAllOperateurs.and.returnValue(of([]));

    mockModalService = jasmine.createSpyObj('NgbModal', ['open']);

    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [OperateurComponent],
      providers: [
        { provide: OperateurService, useValue: mockOperateurService },
        { provide: NgbModal, useValue: mockModalService }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(OperateurComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('devrait être créé', () => {
    expect(component).toBeTruthy();
  });

  it('devrait initialiser avec une liste vide', () => {
    expect(component.listOperateurs).toEqual([]); // Changed from toBeUndefined to toEqual([])
    expect(component.operateur).toEqual({
      idOperateur: null,
      nom: null,
      prenom: null,
      password: null
    });
  });

  describe('getAllOperateurs', () => {
    it('devrait récupérer les opérateurs avec succès', () => {
      const mockOperateurs: Operateur[] = [
        { idOperateur: 1, nom: 'Op1', prenom: 'Prenom1', password: 'pass1' }
      ];

      mockOperateurService.getAllOperateurs.and.returnValue(of(mockOperateurs));

      component.getAllOperateurs();

      expect(mockOperateurService.getAllOperateurs).toHaveBeenCalled();
      expect(component.listOperateurs).toEqual(mockOperateurs);
    });

    it('devrait gérer les erreurs', () => {
      const errorMessage = 'Erreur de serveur';
      mockOperateurService.getAllOperateurs.and.returnValue(throwError(errorMessage));

      spyOn(console, 'error');
      component.getAllOperateurs();

      expect(console.error).toHaveBeenCalledWith('Error fetching operateurs', errorMessage);
    });
  });

  describe('addOperateur', () => {
    it('devrait ajouter un opérateur avec succès', () => {
      const newOperateur: Operateur = {
        idOperateur: null,
        nom: 'Nouveau',
        prenom: 'Op',
        password: 'pass'
      };

      mockOperateurService.addOperateur.and.returnValue(of(newOperateur));
      spyOn(component, 'getAllOperateurs');

      component.addOperateur(newOperateur);

      expect(mockOperateurService.addOperateur).toHaveBeenCalledWith(newOperateur);
      expect(component.getAllOperateurs).toHaveBeenCalled();
      expect(component.form).toBeFalse();
    });
  });
  describe('deleteOperateur', () => {
    it('devrait supprimer un opérateur avec succès', () => {
      const idToDelete = 1;

      mockOperateurService.deleteOperateur.and.returnValue(of({}));
      spyOn(component, 'getAllOperateurs');

      component.deleteOperateur(idToDelete);

      expect(mockOperateurService.deleteOperateur).toHaveBeenCalledWith(idToDelete);
      expect(component.getAllOperateurs).toHaveBeenCalled();
    });
  });
});
