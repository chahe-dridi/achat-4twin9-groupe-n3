import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { StockComponent } from './stock.component';
import { StockService } from '../shared/Service/Stock.service';
import { Stock } from '../shared/Model/Stock';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { FormsModule } from '@angular/forms';

describe('StockComponent', () => {
  let component: StockComponent;
  let fixture: ComponentFixture<StockComponent>;
  let stockService: jasmine.SpyObj<StockService>;
  let modalServiceSpy: jasmine.SpyObj<NgbModal>;

  const mockStock: Stock = {
    idStock: 1,
    libelleStock: 'Stock A',
    qte: 100,
    qteMin: 10
  };

  const mockStocks: Stock[] = [
    mockStock,
    {
      idStock: 2,
      libelleStock: 'Stock B',
      qte: 200,
      qteMin: 20
    }
  ];

  beforeEach(async () => {
    const stockServiceMock = jasmine.createSpyObj('StockService', ['getAllStocks', 'addStock', 'editStock', 'deleteStock']);
    const modalServiceMock = jasmine.createSpyObj('NgbModal', ['open']);

    await TestBed.configureTestingModule({
      declarations: [StockComponent],
      imports: [FormsModule],
      providers: [
        { provide: StockService, useValue: stockServiceMock },
        { provide: NgbModal, useValue: modalServiceMock }
      ]
    }).compileComponents();

    stockService = TestBed.inject(StockService) as jasmine.SpyObj<StockService>;
    modalServiceSpy = TestBed.inject(NgbModal) as jasmine.SpyObj<NgbModal>;
    stockService.getAllStocks.and.returnValue(of(mockStocks));
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StockComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize with stock list', () => {
    expect(stockService.getAllStocks).toHaveBeenCalled();
    expect(component.listStocks).toEqual(mockStocks);
  });

  it('should initialize empty stock object', () => {
    expect(component.stock).toEqual({
      idStock: null,
      libelleStock: null,
      qte: null,
      qteMin: null
    });
  });

  describe('addStock', () => {
    it('should add a stock and refresh list', fakeAsync(() => {
      const newStock: Stock = {
        idStock: null,
        libelleStock: 'Stock C',
        qte: 300,
        qteMin: 30
      };

      stockService.addStock.and.returnValue(of(newStock));
      stockService.getAllStocks.and.returnValue(of([...mockStocks, newStock]));

      component.addStock(newStock);
      tick();

      expect(stockService.addStock).toHaveBeenCalledWith(newStock);
      expect(stockService.getAllStocks).toHaveBeenCalled();
      expect(component.form).toBeFalse();
    }));
  });

  describe('editStock', () => {
    it('should call editStock on service', () => {
      const updatedStock = { ...mockStock, qte: 150 };
      stockService.editStock.and.returnValue(of(updatedStock));

      component.editStock(updatedStock);

      expect(stockService.editStock).toHaveBeenCalledWith(updatedStock);
    });
  });

  describe('deleteStock', () => {
    it('should delete a stock and refresh list', fakeAsync(() => {
      const stockId = 1;
      stockService.deleteStock.and.returnValue(of({}));
      stockService.getAllStocks.and.returnValue(of(mockStocks.filter(s => s.idStock !== stockId)));

      component.deleteStock(stockId);
      tick();

      expect(stockService.deleteStock).toHaveBeenCalledWith(stockId);
      expect(stockService.getAllStocks).toHaveBeenCalled();
    }));
  });
});
