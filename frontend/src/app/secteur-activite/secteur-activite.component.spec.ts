import { TestBed } from '@angular/core/testing';
import { SecteurActiviteService } from '../shared/Service/Secteur-activite.service';
describe('SecteurActiviteService', () => {
  let service: SecteurActiviteService;
  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SecteurActiviteService);
  });
  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});